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
import retrofit2.awaitResponse
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

    val cart = mutableListOf(
        SkuDTO(
            14,
            "Хлеб",
            "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
            5,
            1
        ), SkuDTO(
            4,
            "Рофлы",
            "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
            1,
            5
        ), SkuDTO(
            6,
            "Ярослав",
            "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
            1,
            8
        ), SkuDTO(
            777,
            "Вайбы",
            "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
            77,
            1
        )
    )


//    fun fetchItems() {
//        viewModelScope.launch {
//            dataStoreManager.getUid().collect { uid ->
//                Log.i("uid", uid.toString())
//                if (uid != -1L) {
//
//                    val getHolodosByUserIdResp = itemApi.getHolodosByUserId(uid).awaitResponse()
//                    if (getHolodosByUserIdResp.code() == 200) {
//                        if (getHolodosByUserIdResp.body()!!.isNotEmpty()) {
//                            val getProductsResp = itemApi.getProducts(
//                                uid, getHolodosByUserIdResp.body()!!.first().id!!
//                            ).awaitResponse()
//                            if (getProductsResp.code() == 200) {
//                                _items.update { getHolodosByUserIdResp.body()!!.first().products!! }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }



    private fun createHolodosForUser() {
        viewModelScope.launch {}
    }
}