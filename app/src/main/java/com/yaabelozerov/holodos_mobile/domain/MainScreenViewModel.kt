package com.yaabelozerov.holodos_mobile.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainScreenViewModel: ViewModel() {
    private val _items = MutableStateFlow(emptyList<Pair<String, Int>>())
    val items = _items.asStateFlow()

    init {
        fetchItems()
    }

    fun fetchItems() {
        _items.update {
            listOf(
                Pair("Молоко", 5),
            )
        }
    }
}