package com.yaabelozerov.holodos_mobile.data

data class ItemDTO(
    val id: Long,
    val name: String,
    val daysUntilExpiry: Int,
    val quantity: Int,
)