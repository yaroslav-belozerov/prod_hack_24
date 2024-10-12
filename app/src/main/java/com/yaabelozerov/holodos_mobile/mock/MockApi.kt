package com.yaabelozerov.holodos_mobile.mock

import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.LoginDTO
import com.yaabelozerov.holodos_mobile.data.UserDTO
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MockApi: HolodosService {
    val items = mutableListOf(ItemDTO(0,"Молоко", 5, 1),
        ItemDTO(1,"Говно", -1, 1),
        ItemDTO(2,"Ярослва", 1, 1),
        ItemDTO(32,"Бычья мошонка", 77, 1))
//    val users = mutableMapOf<Long, Pair<Int, String>>()
    var currName = MutableStateFlow("Ярслав")
    var currAvatar = MutableStateFlow(0)

    override suspend fun getFridgeItems(): List<ItemDTO> {
        return items
    }

    override suspend fun register(data: UserDTO): Long {
        return 0
    }

    override suspend fun login(data: LoginDTO): String {
        return "example_token"
    }

    override suspend fun getUser(id: Long): UserDTO {
        return UserDTO("example_email", currName.value, "example_password", currAvatar.value)
    }

    override suspend fun setName(name: String) {
        currName.update { name }
    }

    override suspend fun setAvatarIndex(avatarIndex: Int) {
        currAvatar.update { avatarIndex }
    }
}