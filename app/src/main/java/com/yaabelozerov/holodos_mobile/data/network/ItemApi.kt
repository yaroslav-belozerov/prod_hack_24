package com.yaabelozerov.holodos_mobile.data.network

import com.yaabelozerov.holodos_mobile.data.model.ItemDTO
import com.yaabelozerov.holodos_mobile.domain.network.service.ItemService
import retrofit2.Retrofit
import retrofit2.create

class ItemApi(private val retrofit: Retrofit): ItemService {
    override suspend fun getFridgeItems(): List<ItemDTO> {
        return retrofit.create(ItemService::class.java).getFridgeItems()
    }

}