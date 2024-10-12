package com.yaabelozerov.holodos_mobile.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.yaabelozerov.holodos_mobile.data.UserDTO
import com.yaabelozerov.holodos_mobile.di.AppModule
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val api: HolodosService): ViewModel() {
    private val _userState = MutableStateFlow(UserDTO("", "", "", 0))
    val userState = _userState.asStateFlow()

    init {
        fetchSettings()
    }

    fun fetchSettings() {
        viewModelScope.launch {
            _userState.update {
                api.getUser(0)
            }
        }
    }
}