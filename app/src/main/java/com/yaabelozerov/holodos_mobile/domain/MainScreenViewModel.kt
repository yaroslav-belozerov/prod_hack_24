package com.yaabelozerov.holodos_mobile.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Sorting(val res: Int) {
    NONE(R.string.none), EXPIRY_DATE(R.string.by_expiry_date), QUANTITY(R.string.by_quantity)
}

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val itemApi: HolodosService): ViewModel() {
    private val _items = MutableStateFlow(emptyList<ItemDTO>())
    val items = _items.asStateFlow()

    private val _sort = MutableStateFlow(Sorting.NONE)
    val sort = _sort.asStateFlow()

    init {
        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            _items.update {
                val items = itemApi.getProductsByHolodos(itemApi.getHolodosByUserId(0).id)
                when (sort.value) {
                    Sorting.EXPIRY_DATE -> items.sortedBy { it.daysUntilExpiry }
                    Sorting.QUANTITY -> items.sortedBy { it.quantity }
                    else -> items
                }
            }
        }
    }

    fun setSorting(sorting: Sorting) {
        _sort.update { sorting }
        fetchItems()
    }
}