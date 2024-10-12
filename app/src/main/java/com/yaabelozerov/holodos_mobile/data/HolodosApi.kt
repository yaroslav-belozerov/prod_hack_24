package com.yaabelozerov.holodos_mobile.data

import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import retrofit2.Retrofit

class HolodosApi(private val retrofit: Retrofit): HolodosService {
    override suspend fun getFridgeItems(): List<ItemDTO> {
        return retrofit.create(HolodosService::class.java).getFridgeItems()
    }

    override suspend fun register(data: UserDTO): Long {
        TODO("Not yet implemented")
    }

    override suspend fun login(data: LoginDTO): String {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(id: Long): UserDTO {
        TODO("Not yet implemented")
    }

    override suspend fun setName(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun setAvatarIndex(avatarIndex: Int) {
        TODO("Not yet implemented")
    }
}