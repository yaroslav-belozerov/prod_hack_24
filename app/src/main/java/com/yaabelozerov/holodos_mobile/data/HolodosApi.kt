package com.yaabelozerov.holodos_mobile.data

import com.yaabelozerov.holodos_mobile.di.AppModule
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class HolodosApi(private val retrofit: Retrofit, private val dataStoreManager: AppModule.DataStoreManager): HolodosService {
    override fun serverStatus(): Call<ResponseBody> {
        return retrofit.create(HolodosService::class.java).serverStatus()
    }

    override suspend fun getFridgeItems(): List<ItemDTO> {
        return retrofit.create(HolodosService::class.java).getFridgeItems()
    }

    override suspend fun login(number: String): Long {
        TODO("Not yet implemented")
    }

    override suspend fun addUser(data: UserDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(id: Long): UserDTO {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: UserDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun getCartItems(): List<SkuDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getUsers(id: Long): List<UserDTO> {
        TODO("Not yet implemented")
    }
}