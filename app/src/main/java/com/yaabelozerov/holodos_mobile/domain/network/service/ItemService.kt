package com.yaabelozerov.holodos_mobile.domain.network.service

import com.yaabelozerov.holodos_mobile.data.model.ItemDTO
import retrofit2.http.Headers

interface ItemService {
    @Headers("Content-Type: application/json")

    suspend fun getFridgeItems(): List<ItemDTO>
}