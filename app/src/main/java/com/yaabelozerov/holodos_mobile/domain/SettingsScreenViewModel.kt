package com.yaabelozerov.holodos_mobile.domain

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.yaabelozerov.holodos_mobile.data.CreateProductDTO
import com.yaabelozerov.holodos_mobile.data.CreateUserDTO
import com.yaabelozerov.holodos_mobile.data.ErrQRDTO
import com.yaabelozerov.holodos_mobile.data.HolodosResponse
import com.yaabelozerov.holodos_mobile.data.QRDTO
import com.yaabelozerov.holodos_mobile.di.AppModule
import com.yaabelozerov.holodos_mobile.domain.network.Data
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    @ApplicationContext private val app: Context,
    private val api: HolodosService,
    private val dataStoreManager: AppModule.DataStoreManager,
    private val moshi: Moshi
) : ViewModel() {
    private val _users = MutableStateFlow(emptyList<CreateUserDTO>())
    val users = _users.asStateFlow()

    private val _userIsChild = MutableStateFlow(true)
    var userIsChild = _userIsChild.asStateFlow()

    private val _current = MutableStateFlow<CreateUserDTO?>(null)
    val current = _current.asStateFlow()

    private val _items = MutableStateFlow(emptyList<CreateProductDTO>())
    val items = _items.asStateFlow()

    private val _sort = MutableStateFlow(Sorting.NONE)
    val sort = _sort.asStateFlow()

    private val _qr = MutableStateFlow(QRDTO())
    val qr = _qr.asStateFlow()

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
                    val getUserByIdResp = api.getUserById(uid).awaitResponse()
                    if (getUserByIdResp.code() != 200) {
                        _loggedIn.update { false }
                        return@collect
                    }
                    _current.update { getUserByIdResp.body()!! }
                    fetchUsers()
                } else _loggedIn.update { false }
            }
        }
    }

    fun addUser(phone: String, isSponsor: Boolean) {
        viewModelScope.launch {
            val getUserByPhoneResp = api.getUserByPhone(phone).awaitResponse()
            if (getUserByPhoneResp.code() == 200) {
                val user = getUserByPhoneResp.body()!!
                api.putUser(user.copy(role = if (isSponsor) "SPONSOR" else "RECEIVER"))
                fetchUsers()
            } else if (getUserByPhoneResp.code() == 404) {
                api.createUser(
                    CreateUserDTO(
                        id = 0,
                        firstName = "Имя",
                        lastName = "Фамилия",
                        phone = phone,
                        holodoses = emptyList(),
                        role = if (isSponsor) "SPONSOR" else "RECEIVER",
                        avatarIndex = 0
                    )
                )
                fetchUsers()
            }
        }
    }

    private fun String.convertNumber(): String {
        var num = this.replace(" ", "")
        if (num.length == 10) return "+7${num}"
        if (num.length == 11 && num[0] == '8') return "+7${num.substring(1)}"
        return num
    }

    // TODO написать acceptItemToCart(Item)
    // TODO написать DeclineItemFromCart(Item)

    fun login(number: String) {
        viewModelScope.launch {
            val r =
                api.getUserByPhone(number)
                    .awaitResponse()
            if (r.body()?.role == "SPONSOR") { // Ладно, я нашел что SPONSOR
                _userIsChild.update { false }
            } else if (r.body()?.role != "SPONSOR") {
                _userIsChild.update { true }
            }
            if (r.code() == 200) {
                dataStoreManager.setUid(r.body()!!.id!!)
            } else if (r.code() == 404) {
                val r = api.createUser(
                    CreateUserDTO(
                        id = 0,
                        firstName = "Имя",
                        lastName = "Фамилия",
                        phone = number.convertNumber(),
                        holodoses = emptyList(),
                        role = "SPONSOR",
                        avatarIndex = 0
                    )
                ).awaitResponse()
                dataStoreManager.setUid(r.body()!!.id!!)
            }
            fetchUid()
        }
    }

    fun fetchUsers() {
        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                if (uid == -1L) return@collect

                val currentUserResp = api.getUserById(uid).awaitResponse()
                if (currentUserResp.code() == 200) {
                    _users.update { listOf(currentUserResp.body()!!) }
                    _current.update { currentUserResp.body()!! }
                } else return@collect

                val holodosByUidResp = api.getHolodosByUserId(uid).awaitResponse()
                Log.d("holodos1", holodosByUidResp.body()!!.toString())
                if (holodosByUidResp.body()!!.isEmpty()) {
                    val createHolodosResp = api.createHolodos(
                        HolodosResponse(
                            id = 0, name = "Холодос!", users = emptyList(), products = emptyList()
                        )
                    ).awaitResponse()
                    if (createHolodosResp.code() == 200) {
                        _hol.update { createHolodosResp.body()!! }
                        api.addUserToHolodos(createHolodosResp.body()!!.id!!, uid).awaitResponse()
                    } else return@collect
                } else {
                    Log.d("holodos2", holodosByUidResp.body()!!.toString())
                    val getHolodosUsersResp =
                        api.getHolodosUsers(holodosByUidResp.body()!!.first().id!!).awaitResponse()
                    if (getHolodosUsersResp.code() == 200) {
                        _users.update { getHolodosUsersResp.body()!! }
                    }
                    Log.d("holodos3", getHolodosUsersResp.body()!!.toString())
                    _hol.update { holodosByUidResp.body()!!.first() }
                }
                fetchItems()
            }
        }
    }

    fun updateUser(u: CreateUserDTO) {
        viewModelScope.launch {
            _users.update { it.map { user -> if (user.id == u.id) u else user } }
            val putUserResp = api.putUser(u).awaitResponse()
            if (putUserResp.code() == 200) {
                fetchUsers()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.setUid(-1L)
            _current.update { null }
            _hol.update { null }
            _users.update { emptyList() }
            _loggedIn.update { false }
        }
    }

    fun fetchItems() {
        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                Log.i("uid", uid.toString())
                if (uid != -1L) {
                    _loggedIn.update { true }
                    _hol.value?.let {
                        it.id?.let { hid ->
                            val getProductsResp = api.getProducts(
                                uid, hid
                            ).awaitResponse()
                            if (getProductsResp.code() == 200) {
                                _items.update { getProductsResp.body()!! }
                            }
                        }
                    }
                }
            }
        }
    }

    fun createItem(itemDTO: CreateProductDTO) {
        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                val createProductResp =
                    api.createProduct(_hol.value!!.id!!, itemDTO).awaitResponse()
                api.addUserToHolodos(_hol.value!!.id!!, uid).awaitResponse()
                if (createProductResp.code() == 200) {
                    fetchItems()
                }
            }
        }
    }


    fun setSorting(sorting: Sorting) {
        _sort.update { sorting }
        fetchItems()
    }

    fun updateProductCount(item: CreateProductDTO, holodosId: Long, newCount: Int) {
        viewModelScope.launch {
            dataStoreManager.getUid().collect { uid ->
                if (uid != -1L) {
                    if (newCount <= 0) {
                        api.deleteProductById(item.id!!).awaitResponse()
                        fetchItems()
                    } else {
                        api.deleteProductById(item.id!!).awaitResponse()
                        api.createProduct(holodosId, item.copy(quantity = newCount)).awaitResponse()
                        api.addUserToHolodos(holodosId, uid).awaitResponse()
                        fetchItems()
                    }
                }
            }
        }
    }

    fun getQrData(qr: String) {
        viewModelScope.launch {
            api.getQrData(Data(qr)).awaitResponse().body()?.string()?.let { s ->
                try {
                    _qr.update { moshi.adapter(QRDTO::class.java).fromJson(s)!! }
                } catch (e: Exception) {
                    val msg = try {
                        val obj = moshi.adapter(ErrQRDTO::class.java).fromJson(s)!!
                        "${obj.data} ${obj.code}"
                    } catch (_: Exception) {
                        s
                    }
                    Log.e("qr", msg)
                }
            }
        }
    }
}