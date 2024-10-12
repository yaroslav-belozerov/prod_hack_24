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

    override suspend fun getProductsByHolodos(id: Long): List<ItemDTO> {
        return retrofit.create(HolodosService::class.java).getProductsByHolodos(id)
    }

    override suspend fun deleteProductFromHolodos(id: Long) {
        return retrofit.create(HolodosService::class.java).deleteProductFromHolodos(id)
    }

    override suspend fun addProductsToHolodos(data: ItemDTO) {
        return retrofit.create(HolodosService::class.java).addProductsToHolodos(data)
    }

    override suspend fun login(number: String): Long {
        return retrofit.create(HolodosService::class.java).login(number)
    }

    override suspend fun addUser(data: UserDTO) {
        return retrofit.create(HolodosService::class.java).addUser(data)
    }

    override suspend fun getUserById(id: Long): UserDTO {
        return retrofit.create(HolodosService::class.java).getUserById(id)
    }

    override suspend fun getUserByPhone(phone: String): UserDTO {
        return retrofit.create(HolodosService::class.java).getUserByPhone(phone)
    }

    override suspend fun updateUser(user: UserDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun getHolodosByUserId(id: Long): HolodosDTO {
        TODO("Not yet implemented")
    }

    override suspend fun getHolodosGroup(id: Long): GroupDTO {
        TODO("Not yet implemented")
    }

    override suspend fun getCartItems(): List<SkuDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductInHolodos(id: Long, count: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getUsers(id: Long): List<UserDTO> {
        TODO("Not yet implemented")
    }
}