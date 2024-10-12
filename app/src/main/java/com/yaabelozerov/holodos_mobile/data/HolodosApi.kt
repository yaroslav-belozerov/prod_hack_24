package com.yaabelozerov.holodos_mobile.data

import com.yaabelozerov.holodos_mobile.di.AppModule
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import retrofit2.Retrofit

class HolodosApi(private val retrofit: Retrofit, private val dataStoreManager: AppModule.DataStoreManager): HolodosService {
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

    override fun getUsers(id: Long): List<UserDTO> {
        TODO("Not yet implemented")
    }
}