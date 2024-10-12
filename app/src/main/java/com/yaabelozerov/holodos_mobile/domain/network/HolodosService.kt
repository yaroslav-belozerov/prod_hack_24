package com.yaabelozerov.holodos_mobile.domain.network

import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.SkuDTO
import com.yaabelozerov.holodos_mobile.data.UserDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface HolodosService {
    @Headers("Content-Type: application/json")

    @GET("/api/status")
    fun serverStatus(): Call<ResponseBody>

    @GET("/api/items")
    suspend fun getFridgeItems(): List<ItemDTO>

    @GET("/api/login")
    suspend fun login(number: String): Long

    @GET("/api/addUser")
    suspend fun addUser(data: UserDTO)

    @GET("/api/users/{id}")
    suspend fun getUser(@Path("id") id: Long): UserDTO

    @POST("/api/updateUser/{id")
    suspend fun updateUser(user: UserDTO)

    @GET("/api/users/{id}")
    suspend fun getUsers(@Path("id") id: Long): List<UserDTO>

    @GET("/api/cartitems")
    suspend fun getCartItems(): List<SkuDTO>
}