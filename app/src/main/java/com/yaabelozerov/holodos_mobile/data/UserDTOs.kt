package com.yaabelozerov.holodos_mobile.data

data class UserDTO(
    val email: String,
    val name: String,
    val password: String,
    val avatarIndex: Int
)

data class LoginDTO(
    val token: String
)