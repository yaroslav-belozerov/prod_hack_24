package com.yaabelozerov.holodos_mobile.data

import com.squareup.moshi.Json
import javax.annotation.processing.Generated

data class UserDTO(
    val id: Long,
    val phone: String,
    val name: String,
    val surname: String,
    val avatarIndex: Int
)

data class CreateUserDTO(
    @Json(name = "id")
    var id: Long? = null,

    @Json(name = "firstName")
    var firstName: String? = null,

    @Json(name = "lastName")
    var lastName: String? = null,

    @Json(name = "phone")
    var phone: String? = null,

    @Json(name = "holodoses")
    var holodoses: List<HolodosResponse>? = null,

    @Json(name = "role")
    var role: String? = null,

    @Json(name = "avatarIndex")
    var avatarIndex: Int? = null,
)


