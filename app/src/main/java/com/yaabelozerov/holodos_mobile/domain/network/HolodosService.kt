package com.yaabelozerov.holodos_mobile.domain.network

import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.LoginDTO
import com.yaabelozerov.holodos_mobile.data.UserDTO
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface HolodosService {
    @Headers("Content-Type: application/json")

    @GET("/api/items")
    suspend fun getFridgeItems(): List<ItemDTO>

    @GET("/api/register")
    suspend fun register(data: UserDTO): Long

    @GET("/api/login")
    suspend fun login(data: LoginDTO): String

    @GET("/api/addUser")
    suspend fun addUer(data: UserDTO)

    @GET("/api/users/{id}")
    suspend fun getUser(@Path("id") id: Long): UserDTO

    @POST("/api/updateUser")
    suspend fun updateUser(user: UserDTO)
}