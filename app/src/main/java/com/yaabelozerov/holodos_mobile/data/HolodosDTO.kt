package com.yaabelozerov.holodos_mobile.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class HolodosResponse (
    @Json(name = "id")
    var id: Long? = null,

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "users")
    var users: List<CreateUserDTO>? = null,

    @Json(name = "products")
    var products: List<CreateProductDTO>? = null,
)