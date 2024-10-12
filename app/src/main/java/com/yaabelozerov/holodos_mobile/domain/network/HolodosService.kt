package com.yaabelozerov.holodos_mobile.domain.network

import com.yaabelozerov.holodos_mobile.data.GroupDTO
import com.yaabelozerov.holodos_mobile.data.HolodosDTO
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.SkuDTO
import com.yaabelozerov.holodos_mobile.data.UserDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HolodosService {
    @GET("/api/getUserById/{id}")
    suspend fun getUserById(@Path("id") id: Long): UserDTO

    @GET("/api/getUserByPhone/")
    suspend fun getUserByPhone(phone: String): UserDTO

    @POST("/api/updateUser/{id}")
    suspend fun updateUser(user: UserDTO)

    @GET("/api/getHolodosByUserId/{id}")
    suspend fun getHolodosByUserId(@Path("id") id: Long): HolodosDTO

    @GET("/api/getHolodosGroup/{id}")
    suspend fun getHolodosGroup(@Path("id") id: Long): GroupDTO

    @GET("/api/status")
    fun serverStatus(): Call<ResponseBody>

    @GET("/api/getProductsByHolodos/{id}")
    suspend fun getProductsByHolodos(@Path("id") id: Long): List<ItemDTO>

    @GET("/api/deleteProductFromHolodos/{id}")
    suspend fun deleteProductFromHolodos(@Path("id") id: Long)

    @POST("/api/addProductToHolodos")
    suspend fun addProductsToHolodos(data: ItemDTO)

    @GET("/api/login")
    suspend fun login(number: String): Long

    @GET("/api/addUser")
    suspend fun addUser(data: UserDTO)

    @GET("/api/users/{id}")
    suspend fun getUsers(@Path("id") id: Long): List<UserDTO>

    @GET("/api/cartitems")
    suspend fun getCartItems(): List<SkuDTO>

    @GET("TODO")
    suspend fun updateProductInHolodos(id: Long, count: Int)
}