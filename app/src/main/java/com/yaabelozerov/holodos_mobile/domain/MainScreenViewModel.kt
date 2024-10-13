package com.yaabelozerov.holodos_mobile.domain

import android.util.Log
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

    fun fetchItems() {
        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                if (uid != -1L) {

                    itemApi.getHolodosByUserId(uid).enqueue(object : Callback<List<HolodosResponse>> {
                        override fun onResponse(
                            p0: Call<List<HolodosResponse>>,
                            p1: Response<List<HolodosResponse>>
                        ) {
                           _items.update { p1.body()!!.firstOrNull()?.products ?: emptyList() }
                        }

                        override fun onFailure(p0: Call<List<HolodosResponse>>, p1: Throwable) {
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