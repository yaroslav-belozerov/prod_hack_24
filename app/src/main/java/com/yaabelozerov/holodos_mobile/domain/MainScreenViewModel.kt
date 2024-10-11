package com.yaabelozerov.holodos_mobile.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaabelozerov.holodos_mobile.data.model.ItemDTO
import com.yaabelozerov.holodos_mobile.domain.network.service.ItemService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val itemApi: ItemService): ViewModel() {
    private val _items = MutableStateFlow(emptyList<ItemDTO>())
    val items = _items.asStateFlow()

    init {
        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            _items.update {
                itemApi.getFridgeItems()
            }
        }
    }
}