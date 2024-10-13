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
import com.yaabelozerov.holodos_mobile.di.AppModule
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    init {
        fetchItems()
    }

    fun createItem(itemDTO: CreateProductDTO, holodosId: Long) {
        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                itemApi.createProduct(uid, holodosId, itemDTO)
                    .enqueue(object : Callback<CreateProductDTO> {
                        override fun onResponse(
                            p0: Call<CreateProductDTO>,
                            p1: Response<CreateProductDTO>
                        ) {
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
        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                if (uid != -1L) {
                    itemApi.getHolodosByUserId(uid)
                        .enqueue(object : Callback<List<HolodosResponse>> {
                            override fun onResponse(
                                p0: Call<List<HolodosResponse>>,
                                p1: Response<List<HolodosResponse>>
                            ) {
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
        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                if (uid != -1L) {
                    if (newCount <= 0) {
                        itemApi.deleteProductById(item.id!!)
                    } else {
                        itemApi.createProduct(uid, holodosId, item.copy(quantity = newCount))
                    }
                    fetchItems()
                }
            }
        }
    }

    fun getQrData(qr: String, ) {
        viewModelScope.launch {
            itemApi.getQrData(qr).enqueue(object : Callback<QRDTO> {
                override fun onResponse(p0: Call<QRDTO>, p1: Response<QRDTO>) {
                    Log.i("getQrData", "${p1.code()} ${p1.message()}")
                }

                override fun onFailure(p0: Call<QRDTO>, p1: Throwable) {
                    Log.e("getQrData", p1.stackTraceToString())
                }

            })
        }
    }
}