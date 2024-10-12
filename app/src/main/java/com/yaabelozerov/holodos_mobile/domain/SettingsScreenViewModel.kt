package com.yaabelozerov.holodos_mobile.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaabelozerov.holodos_mobile.C
import com.yaabelozerov.holodos_mobile.data.UserDTO
import com.yaabelozerov.holodos_mobile.di.AppModule
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

data class LoginState(
    val number: String,
    val uid: Long,
    val isLoggedIn: Boolean,
)

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val api: HolodosService, private val dataStoreManager: AppModule.DataStoreManager): ViewModel() {
    private val _users = MutableStateFlow(emptyList<UserDTO>())
    val users = _users.asStateFlow()

    private val _currentId = MutableStateFlow<Long?>(null)
    val currentId = _currentId.asStateFlow()

    private val _code = MutableStateFlow(0)
    val code = _code.asStateFlow()

    init {
        serverStatus()
    }

    fun serverStatus() {
        viewModelScope.launch {
            val retrofit = Retrofit.Builder().baseUrl(C.BASE_URL).build()
            retrofit.create(HolodosService::class.java).serverStatus().enqueue(object: Callback<ResponseBody> {
                override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                    _code.update { p1.code() }
                }

                override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                    p1.printStackTrace()
                    _code.update { -100 }
                }

            })

        }
    }

    fun login(number: String) {
        viewModelScope.launch {
            _currentId.update {
                val uid = api.login(number)
                dataStoreManager.setUid(uid)
                uid
            }
        }
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _users.update {
                api.getUsers(currentId.value!!)
            }
        }
    }

    fun updateUser(user: UserDTO) {
        viewModelScope.launch {
            api.updateUser(user)
            fetchUsers()
        }
    }
}