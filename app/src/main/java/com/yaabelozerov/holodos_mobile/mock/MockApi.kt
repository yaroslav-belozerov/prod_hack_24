package com.yaabelozerov.holodos_mobile.mock

import com.yaabelozerov.holodos_mobile.C
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.SkuDTO
import com.yaabelozerov.holodos_mobile.data.UserDTO
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Path

class MockApi: HolodosService {
    val items = mutableListOf(ItemDTO(0,"Молоко", 5, 1),
        ItemDTO(1,"Дедлайн", -1, 1),
        ItemDTO(2,"Ярослав", 1, 1),
        ItemDTO(32,"Вайбы", 77, 1))
    val cart = mutableListOf(SkuDTO(14,"Хлеб", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 5, 1),
        SkuDTO(4,"Рофлы", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 1, 5),
        SkuDTO(6,"Ярослав", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 1, 8),
        SkuDTO(777,"Вайбы", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 77, 1))
    var accounts = listOf<UserDTO>(
        UserDTO(0, "asdasd", "Asdasd", "Asdasd", 0),
        UserDTO(1, "asddcccc", "mmmmmm", "Asdasd", 0),
    )

    override fun serverStatus(): Call<ResponseBody> {
        val retrofit = Retrofit.Builder().baseUrl(C.BASE_URL).build()
        return retrofit.create(HolodosService::class.java).serverStatus()
    }

    override suspend fun getFridgeItems(): List<ItemDTO> {
        return items
    }

    override suspend fun login(number: String): Long {
        return 0
    }

    override suspend fun addUser(data: UserDTO) {
        accounts = accounts.plus(data)
    }

    override suspend fun getUser(id: Long): UserDTO {
        return accounts.find { it.id == id }!!
    }

    override suspend fun updateUser(user: UserDTO) {
        val u = accounts.indexOfFirst { it.id == user.id }
        val mut = accounts.toMutableList()
        mut[u] = user
        accounts = mut
    }

    override suspend fun getCartItems(): List<SkuDTO> {
        return cart
    }

    override suspend fun getUsers(id: Long): List<UserDTO> {
        return accounts
    }
}