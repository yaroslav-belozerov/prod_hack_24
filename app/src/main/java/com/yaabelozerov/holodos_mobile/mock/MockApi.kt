package com.yaabelozerov.holodos_mobile.mock

import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.LoginDTO
import com.yaabelozerov.holodos_mobile.data.UserDTO
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MockApi: HolodosService {
    val items = mutableListOf(ItemDTO(0,"Молоко", 5, 1),
        ItemDTO(1,"Дедлайн", -1, 1),
        ItemDTO(2,"Ярослав", 1, 1),
        ItemDTO(32,"Вайбы", 77, 1))
    val users = mutableListOf<UserDTO>(
        UserDTO(0, "asdasd", "Asdasd", "Asdasd", 0),
    )

    override suspend fun getFridgeItems(): List<ItemDTO> {
        return items
    }

    override suspend fun register(data: UserDTO): Long {
        return 0
    }

    override suspend fun login(data: LoginDTO): String {
        return "example_token"
    }

    override suspend fun addUer(data: UserDTO) {
        users.add(data)
    }

    override suspend fun getUser(id: Long): UserDTO {
        return users.find { it.id == id }!!
    }

    override suspend fun updateUser(user: UserDTO) {
        val u = users.indexOfFirst { it.id == user.id }
        users[u] = user
    }
}