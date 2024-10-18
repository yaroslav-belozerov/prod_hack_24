package com.yaabelozerov.holodos_mobile.data

import com.squareup.moshi.Json


data class SkuDTO(
    val id: Long, val name: String, val pictureURL: String?, val quantity: Int, val bestBefore: Int
)

data class Sku(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "pictureUrl") val pictureUrl: String? = null,
    @Json(name = "bestBeforeDays") val bestBeforeDays: Int? = null,
    @Json(name = "products") val products: List<String>? = null,
)

data class CreateProductDTO(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "sku") val sku: Sku? = null,
    @Json(name = "holodos") val holodos: HolodosResponse? = null,
    @Json(name = "quantity") val quantity: Int? = null,
    @Json(name = "dateMade") val dateMade: String? = null,
    @Json(name = "owner") val owner: CreateUserDTO? = null,
)