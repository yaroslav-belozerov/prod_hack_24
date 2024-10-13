package com.yaabelozerov.holodos_mobile.domain

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.CreateProductDTO
import com.yaabelozerov.holodos_mobile.data.HolodosResponse
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.QRDTO
import com.yaabelozerov.holodos_mobile.data.SkuDTO
import com.yaabelozerov.holodos_mobile.di.AppModule
import com.yaabelozerov.holodos_mobile.domain.network.Data
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

enum class Sorting(val res: Int) {
    NONE(R.string.none), EXPIRY_DATE(R.string.by_expiry_date), QUANTITY(R.string.by_quantity)
}

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    @ApplicationContext private val app: Context,
    private val itemApi: HolodosService,
    private val dataStoreManager: AppModule.DataStoreManager
) : ViewModel() {
    private val _items = MutableStateFlow(emptyList<CreateProductDTO>())
    val items = _items.asStateFlow()

    private val _sort = MutableStateFlow(Sorting.NONE)
    val sort = _sort.asStateFlow()

    private val _qr = MutableStateFlow(QRDTO())
    val qr = _qr.asStateFlow()

    private var id = MutableStateFlow(0)

    val cart = mutableListOf(
        SkuDTO(14,"Хлеб", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 5, 1),
        SkuDTO(4,"Рофлы", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 1, 5),
        SkuDTO(6,"Ярослав", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 1, 8),
        SkuDTO(777,"Вайбы", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 77, 1)
    )

    init {
        fetchItems()
    }

    fun createItem(itemDTO: CreateProductDTO, holodosId: Long, days: Int) {
        _items.update { it.plus(itemDTO.copy(id = id.value.toLong(), quantity = 1, dateMade = days.toString())) }
        id.update { it+1 }
        return

        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                itemApi.createProduct(uid, holodosId, itemDTO)
                    .enqueue(object : Callback<CreateProductDTO> {
                        override fun onResponse(
                            p0: Call<CreateProductDTO>,
                            p1: Response<CreateProductDTO>
                        ) {
                            Log.d("resp id", "$uid $holodosId $itemDTO")
                            Log.d("resp", p1.body().toString() + " " + p1.code().toString())
                            fetchItems()
                        }

                        override fun onFailure(p0: Call<CreateProductDTO>, p1: Throwable) {
                            println(p0.request().url())
                            println(p0.request().method())
                            p1.printStackTrace()
                            Toast.makeText(app, "Ошибка в создании товара", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
            }
        }
    }

    fun fetchItems() {
        when (_sort.value) {
            Sorting.EXPIRY_DATE -> _items.update { it.sortedByDescending { it.dateMade } }
            Sorting.QUANTITY -> _items.update { it.sortedByDescending { it.quantity } }
            Sorting.NONE -> _items.update { it }
        }
        return

        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                if (uid != -1L) {
                    itemApi.getHolodosByUserId(uid)
                        .enqueue(object : Callback<List<HolodosResponse>> {
                            override fun onResponse(
                                p0: Call<List<HolodosResponse>>,
                                p1: Response<List<HolodosResponse>>
                            ) {
                                Log.e("response", p1.body().toString())
                                if (p1.code() == 200) {
                                    if (p1.body()!!.isNotEmpty()) itemApi.getProducts(
                                        uid,
                                        p1.body()!!.first()!!.id!!
                                    ).enqueue(object : Callback<List<CreateProductDTO>> {
                                        override fun onResponse(
                                            p0: Call<List<CreateProductDTO>>,
                                            p1: Response<List<CreateProductDTO>>
                                        ) {
                                            if (p1.code() == 200) {
                                                _items.update { p1.body()!! }
                                            }
                                            Log.d("get prducts", p1.body().toString() + " " + p1.code().toString())
                                        }

                                        override fun onFailure(
                                            p0: Call<List<CreateProductDTO>>,
                                            p1: Throwable
                                        ) {
                                            println(p0.request().url())
                                            println(p0.request().method())
                                            p1.printStackTrace()
                                        }

                                    })
                                } else {
                                    Log.e("fetchItems", p1.errorBody().toString())
                                }
                            }

                            override fun onFailure(p0: Call<List<HolodosResponse>>, p1: Throwable) {
                                println(p0.request().url())
                                println(p0.request().method())
                                p1.printStackTrace()
                            }
                        })
                }
            }
        }
    }

    fun setSorting(sorting: Sorting) {
        _sort.update { sorting }
        fetchItems()
    }

    fun updateProductCount(item: CreateProductDTO, holodosId: Long, newCount: Int) {
        val ind = _items.value.indexOfFirst { it.id == item.id }
        if (newCount == 0) _items.update { it.filter { el -> el.id != item.id } }
        else _items.update { it.map { el -> if (el.id == item.id) el.copy(quantity = newCount) else el } }
        return

        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                if (uid != -1L) {
                    if (newCount <= 0) {
                        itemApi.deleteProductById(item.id!!)
                    } else {
                        itemApi.deleteProductById(item.id!!).enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                                Log.d("del", p1.code().toString())
                            }

                            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                                p1.printStackTrace()
                            }

                        })
                        itemApi.createProduct(uid, holodosId, item.copy(quantity = newCount)).enqueue(object : Callback<CreateProductDTO> {
                            override fun onResponse(
                                p0: Call<CreateProductDTO>,
                                p1: Response<CreateProductDTO>
                            ) {
                                Log.d("upd", p1.body().toString() + " " + p1.code().toString())
                            }

                            override fun onFailure(p0: Call<CreateProductDTO>, p1: Throwable) {
                                p1.printStackTrace()
                            }

                        })
                    }
                    fetchItems()
                }
            }
        }
    }

    fun getQrData(qr: String, ) {
        viewModelScope.launch {
            itemApi.getQrData(Data(qr)).enqueue(object : Callback<QRDTO> {
                override fun onResponse(p0: Call<QRDTO>, p1: Response<QRDTO>) {
                    Log.i("getQrData", "${p1.code()} ${p1.message()}")
                    if (p1.code() == 200) {
                        _qr.update { p1.body()!! }
                    }
                }

                override fun onFailure(p0: Call<QRDTO>, p1: Throwable) {
                    Log.e("getQrData", p1.stackTraceToString())
                }

            })
        }
    }
}