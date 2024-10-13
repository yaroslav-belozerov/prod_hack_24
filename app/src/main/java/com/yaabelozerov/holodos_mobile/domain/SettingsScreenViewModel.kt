package com.yaabelozerov.holodos_mobile.domain

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaabelozerov.holodos_mobile.data.CreateUserDTO
import com.yaabelozerov.holodos_mobile.data.HolodosResponse
import com.yaabelozerov.holodos_mobile.di.AppModule
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

data class LoginState(
    val number: String,
    val uid: Long,
    val isLoggedIn: Boolean,
)

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    @ApplicationContext private val app: Context,
    private val api: HolodosService,
    private val dataStoreManager: AppModule.DataStoreManager
) : ViewModel() {
    private val _users = MutableStateFlow(emptyList<CreateUserDTO>())
    val users = _users.asStateFlow()

    private val _current = MutableStateFlow<CreateUserDTO?>(null)
    val current = _current.asStateFlow()

    private val _hol = MutableStateFlow<HolodosResponse?>(null)
    val hol = _hol.asStateFlow()

    private val _code = MutableStateFlow(0)
    val code = _code.asStateFlow()

    private val _loggedIn = MutableStateFlow<Boolean?>(null)
    val loggedIn = _loggedIn.asStateFlow()

    init {
        fetchUid()
    }

    fun fetchUid() {
        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                if (uid != -1L) {
                    _loggedIn.update { true }
                    api.getUserById(uid).enqueue(object : Callback<CreateUserDTO> {
                        override fun onResponse(
                            p0: Call<CreateUserDTO>,
                            p1: Response<CreateUserDTO>
                        ) {
                            if (p1.code() == 200) {
                                _current.update { p1.body()!! }
                            } else {
                                Log.e("fetchUid", p1.errorBody().toString())
                            }
                        }

                        override fun onFailure(p0: Call<CreateUserDTO>, p1: Throwable) {
                            println(p0.request().url())
                            println(p0.request().method())
                            p1.printStackTrace()
                        }
                    })
                    fetchUsers()
                } else _loggedIn.update { false }
            }
        }
    }

    fun addUser(phone: String, isSponsor: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                api.getUserByPhone(phone).enqueue(object : Callback<CreateUserDTO> {
                    override fun onResponse(p0: Call<CreateUserDTO>, p1: Response<CreateUserDTO>) {
                        if (p1.code() == 200) {
                            val user = p1.body()!!
                            api.putUser(user.copy(role = if (isSponsor) "SPONSOR" else "RECEIVER"))
                            fetchUsers()
                        } else if (p1.code() == 404) {
                            api.createUser(
                                CreateUserDTO(
                                    firstName = "Имя",
                                    lastName = "Фамилия",
                                    phone = phone,
                                    role = if (isSponsor) "SPONSOR" else "RECEIVER"
                                )
                            )
                            fetchUsers()
                        }
                    }

                    override fun onFailure(p0: Call<CreateUserDTO>, p1: Throwable) {
                        Toast.makeText(app, "Ошибка создания пользователя", Toast.LENGTH_SHORT).show()
                        println(p0.request().url())
                        println(p0.request().method())
                        p1.printStackTrace()
                    }
                })
            }
        }
    }

    private fun String.convertNumber(): String {
        var num = this.replace(" ", "")
        if (num.length == 10) return "+7${num}"
        if (num.length == 11 && num[0] == '8') return "+7${num.substring(1)}"
        return num
    }

    fun login(number: String) {
        viewModelScope.launch {
            api.getUserByPhone(number.convertNumber().also { Log.i("converted_number", it) })
                .enqueue(
                    object : Callback<CreateUserDTO> {
                        override fun onResponse(
                            p0: Call<CreateUserDTO>,
                            p1: Response<CreateUserDTO>
                        ) {
                            if (p1.code() == 200) {
                                viewModelScope.launch {
                                    dataStoreManager.setUid(p1.body()!!.id!!)
                                    fetchUid()
                                }
                            } else if (p1.code() == 404) {
                                viewModelScope.launch {
                                    withContext(Dispatchers.IO) {
                                        api.getUserByPhone(number).enqueue(object : Callback<CreateUserDTO> {
                                            override fun onResponse(
                                                p0: Call<CreateUserDTO>,
                                                p1: Response<CreateUserDTO>
                                            ) {
                                                if (p1.code() == 200) {
                                                    viewModelScope.launch {
                                                        dataStoreManager.setUid(p1.body()!!.id!!)
                                                        fetchUid()
                                                    }
                                                } else {
                                                    api.createUser(
                                                        CreateUserDTO(
                                                            firstName = "Имя",
                                                            lastName = "Фамилия",
                                                            phone = number.convertNumber(),
                                                            role = "SPONSOR"
                                                        )
                                                    ).enqueue(object : Callback<CreateUserDTO> {
                                                        override fun onResponse(
                                                            p0: Call<CreateUserDTO>,
                                                            p1: Response<CreateUserDTO>
                                                        ) {
                                                            viewModelScope.launch {
                                                                Log.i("createUser", p1.code().toString() + " " + p1.message())
                                                                dataStoreManager.setUid(p1.body()!!.id!!)
                                                                fetchUid()
                                                            }
                                                        }

                                                        override fun onFailure(
                                                            p0: Call<CreateUserDTO>,
                                                            p1: Throwable
                                                        ) {
                                                            Toast.makeText(app, "Ошибка создания пользователя", Toast.LENGTH_SHORT).show()
                                                            println(p0.request().url())
                                                            println(p0.request().method())
                                                            p1.printStackTrace()
                                                        }

                                                    })
                                                }
                                            }

                                            override fun onFailure(
                                                p0: Call<CreateUserDTO>,
                                                p1: Throwable
                                            ) {
                                                println(p0.request().url())
                                                println(p0.request().method())
                                                p1.printStackTrace()
                                            }

                                        })
                                    }
                                }
                            }
                        }

                        override fun onFailure(p0: Call<CreateUserDTO>, p1: Throwable) {
                            Toast.makeText(app, "Ошибка автоирзации", Toast.LENGTH_SHORT).show()
                            println(p0.request().url())
                            println(p0.request().method())
                            p1.printStackTrace()
                        }
                    })
        }
    }

    fun fetchUsers() {
        viewModelScope.launch {
                dataStoreManager.getUid().collect { uid ->
                    if (uid != -1L) {
                        api.getUserById(uid).enqueue(object : Callback<CreateUserDTO> {
                            override fun onResponse(
                                p0: Call<CreateUserDTO>,
                                p1: Response<CreateUserDTO>
                            ) {
                                if (p1.code() == 200) {
                                    Log.i("info", "${p1.body()} ${p1.code()} $uid")
                                    _users.update { listOf(p1.body()!!) }
                                } else {
                                    Toast.makeText(app, "Ошибка получения пользователей 1", Toast.LENGTH_SHORT).show()
                                    Log.e("getUsers1", uid.toString() + " " + p1.code().toString() + " " + p1.message())
                                }
                            }

                            override fun onFailure(p0: Call<CreateUserDTO>, p1: Throwable) {
                                println(p0.request().url())
                                println(p0.request().method())
                                p1.printStackTrace()
                            }

                        })
                        api.getHolodosByUserId(uid).enqueue(object : Callback<List<HolodosResponse>> {
                            override fun onResponse(
                                p0: Call<List<HolodosResponse>>,
                                p1: Response<List<HolodosResponse>>
                            ) {
                                if (p1.body()!!.isEmpty()) {
                                   api.getUserById(uid.also { Log.i("uid get golodos", it.toString()) }).enqueue(object : Callback<CreateUserDTO> {
                                       override fun onResponse(
                                           p0: Call<CreateUserDTO>,
                                           p1: Response<CreateUserDTO>
                                       ) {
                                           if (p1.code() == 200) {
                                               api.createHolodos(
                                                   HolodosResponse(
                                                       id = null,
                                                       name = "Холодос!",
                                                       users = listOf(),
                                                       products = listOf()
                                                   )
                                               ).enqueue(object : Callback<HolodosResponse> {
                                                   override fun onResponse(
                                                       p0: Call<HolodosResponse>,
                                                       p1: Response<HolodosResponse>
                                                   ) {
                                                       if (p1.code() == 200) {
                                                           _hol.update { p1.body()!! }
                                                           api.addUserToHolodos(p1.body()!!.id!!, uid).enqueue(object : Callback<HolodosResponse> {
                                                               override fun onResponse(
                                                                   p0: Call<HolodosResponse>,
                                                                   p1: Response<HolodosResponse>
                                                               ) {
                                                                   if (p1.code() == 200) {
                                                                        _users.update { p1.body()!!.users!! }
                                                                   } else {
                                                                       Log.e("getUsers", uid.toString() + " " + p1.code().toString() + " " + p1.message())
                                                                   }
                                                               }

                                                               override fun onFailure(
                                                                   p0: Call<HolodosResponse>,
                                                                   p1: Throwable
                                                               ) {
                                                                   println(p0.request().url())
                                                                   println(p0.request().method())
                                                                   p1.printStackTrace()
                                                               }

                                                           })
                                                       } else {
                                                           Log.e("getUsers2", uid.toString() + " " + p1.code().toString() + " " + p1.message())
                                                       }
                                                   }

                                                   override fun onFailure(
                                                       p0: Call<HolodosResponse>,
                                                       p1: Throwable
                                                   ) {
                                                       println(p0.request().url())
                                                       println(p0.request().method())
                                                       p1.printStackTrace()
                                                   }

                                               })
                                           } else {
                                               Log.e("getUsers3", uid.toString() + " " + p1.code().toString() + " " + p1.message())
                                           }
                                       }

                                       override fun onFailure(
                                           p0: Call<CreateUserDTO>,
                                           p1: Throwable
                                       ) {
                                           println(p0.request().url())
                                           println(p0.request().method())
                                           p1.printStackTrace()
                                       }
                                   })
                                } else {
                                    _users.update { p1.body()!!.firstOrNull()?.users ?: emptyList() } // TODO cyclic holodos
                                    Log.d("users", "users: ${_users.value}")
                                }
                            }

                            override fun onFailure(p0: Call<List<HolodosResponse>>, p1: Throwable) {
                                println(p0.request().url())
                                println(p0.request().method())
                                p1.printStackTrace()
                               Toast.makeText(app, "Ошибка получения пользователей 2", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )}
                }
            }
    }

    fun updateUser(u: CreateUserDTO) {
        viewModelScope.launch {
            api.putUser(
                u
            ).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                    if (p1.code() != 200) {
                        Log.e("updateUser", p1.code().toString() + " " + p1.message())
                    } else {
                        fetchUsers()
                    }
                }

                override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                    println(p0.request().url())
                    println(p0.request().method())
                    p1.printStackTrace()
                }

            })
        }
    }
}