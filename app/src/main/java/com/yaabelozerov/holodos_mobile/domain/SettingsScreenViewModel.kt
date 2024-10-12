package com.yaabelozerov.holodos_mobile.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.yaabelozerov.holodos_mobile.di.AppModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val dataStoreManager: AppModule.DataStoreManager, private val moshi: Moshi): ViewModel() {
    fun fetchSettings() {
        viewModelScope.launch {

        }
    }
}