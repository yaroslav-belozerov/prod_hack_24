package com.yaabelozerov.holodos_mobile.data

import com.squareup.moshi.Json

data class QRDTO (
    @Json(name = "code")
    var code: Int? = null,

    @Json(name = "first")
    var first: Int? = null,

    @Json(name = "data")
    var data: Data? = null,
)

data class ErrQRDTO(
    @Json(name = "code")
    val code: Int? = null,

    @Json(name = "data")
    var data: String? = null,
)

data class Data (
    @Json(name = "json")
    var json: com.yaabelozerov.holodos_mobile.data.Json? = null,

    @Json(name = "html")
    var html: String? = null,
)

data class Json (
    @Json(name = "items")
    var items: List<Item>? = null,
)
data class Item (
    @Json(name = "nds")
    var nds: Int? = null,

    @Json(name = "sum")
    var sum: Int? = null,

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "price")
    var price: Int? = null,

    @Json(name = "quantity")
    var quantity: Int? = null,
)