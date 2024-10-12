package com.yaabelozerov.holodos_mobile.data

data class UserDTO(
    val id: Long,
    val email: String,
    val name: String,
    val password: String,
    val avatarIndex: Int
)

data class LoginDTO(
    val email: String,
    val password: String
)