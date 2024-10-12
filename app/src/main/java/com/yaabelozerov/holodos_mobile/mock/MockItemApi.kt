package com.yaabelozerov.holodos_mobile.mock

import com.yaabelozerov.holodos_mobile.data.model.ItemDTO
import com.yaabelozerov.holodos_mobile.domain.network.service.ItemService

class MockItemApi: ItemService {
    override suspend fun getFridgeItems(): List<ItemDTO> {
        return listOf(
            ItemDTO("Молоко", 5, 1),
            ItemDTO("Говно", -1, 1),
            ItemDTO("Ярослав", 1, 1),
            ItemDTO("Бычья мошонка", 77, 1),
        )
    }
}