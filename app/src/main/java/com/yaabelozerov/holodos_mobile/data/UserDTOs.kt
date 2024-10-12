package com.yaabelozerov.holodos_mobile.data

data class UserDTO(
    val id: Long,
    val phone: String,
    val name: String,
    val surname: String,
    val avatarIndex: Int
)