package com.yaabelozerov.holodos_mobile.domain.network

import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.LoginDTO
import com.yaabelozerov.holodos_mobile.data.UserDTO
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface HolodosService {
    @Headers("Content-Type: application/json")

    @GET("/api/items")
    suspend fun getFridgeItems(): List<ItemDTO>

    @GET("/api/register")
    suspend fun register(data: UserDTO): Long

    @GET("/api/login")
    suspend fun login(data: LoginDTO): String

    @GET("/api/users/{id}")
    suspend fun getUser(@Path("id") id: Long): UserDTO

    @GET("/api/setName")
    suspend fun setName(name: String)

    @GET("/api/setAvatarIndex")
    suspend fun setAvatarIndex(avatarIndex: Int)
}