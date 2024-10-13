package com.yaabelozerov.holodos_mobile.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.SkuDTO
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(private val itemApi: HolodosService): ViewModel() {
    private val _items = MutableStateFlow(emptyList<SkuDTO>())
    val items = _items.asStateFlow()

    init {
        fetchItems()
    }
    private fun fetchItems() {
        viewModelScope.launch {

        }
    }
}