package com.yaabelozerov.holodos_mobile.data

data class ItemDTO(
    val id: Long,
    val name: String,
    val daysUntilExpiry: Int,
    val quantity: Int,
    val holodosId: Long
)

data class HolodosDTO(
    val id: Long,
    val name: String
)

data class GroupDTO(
    val users: List<UserDTO>
)