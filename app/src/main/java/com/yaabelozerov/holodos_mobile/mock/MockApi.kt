package com.yaabelozerov.holodos_mobile.mock

import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.LoginDTO
import com.yaabelozerov.holodos_mobile.data.UserDTO
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService

class MockApi: HolodosService {
    val items = mutableListOf(ItemDTO(0,"Молоко", 5, 1),
        ItemDTO(1,"Дедлайн", -1, 1),
        ItemDTO(2,"Ярослав", 1, 1),
        ItemDTO(32,"Вайбы", 77, 1))
    var accounts = listOf<UserDTO>(
        UserDTO(0, "asdasd", "Asdasd", "Asdasd", 0),
        UserDTO(1, "asddcccc", "mmmmmm", "Asdasd", 0),
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
        accounts = accounts.plus(data)
    }

    override suspend fun getUser(id: Long): UserDTO {
        return accounts.find { it.id == id }!!
    }

    override suspend fun updateUser(user: UserDTO) {
        val u = accounts.indexOfFirst { it.id == user.id }
        val mut = accounts.toMutableList()
        mut[u] = user
        accounts = mut
    }

    override fun getUsers(): List<UserDTO> {
        return accounts
    }
}