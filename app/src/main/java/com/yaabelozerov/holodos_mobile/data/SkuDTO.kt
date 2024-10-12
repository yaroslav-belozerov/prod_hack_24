package com.yaabelozerov.holodos_mobile.data

data class SkuDTO(
    val id: Long,
    val name: String,
    val pictureURL: String?,
    val quantity: Int,
    val bestBefore: Int
)
