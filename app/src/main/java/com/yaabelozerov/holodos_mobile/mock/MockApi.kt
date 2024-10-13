package com.yaabelozerov.holodos_mobile.mock

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.holodos_mobile.C
import com.yaabelozerov.holodos_mobile.data.CreateUserDTO
import com.yaabelozerov.holodos_mobile.data.GroupDTO
import com.yaabelozerov.holodos_mobile.data.HolodosDTO
import com.yaabelozerov.holodos_mobile.data.HolodosResponse
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.QRDTO
import com.yaabelozerov.holodos_mobile.data.Sku
import com.yaabelozerov.holodos_mobile.data.SkuDTO
import com.yaabelozerov.holodos_mobile.data.UserDTO
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Query

class MockApi {
    val items = mutableListOf(ItemDTO(0,"Молоко", 5, 1, 2),
        ItemDTO(1,"Дедлайн", -1, 1, 2),
        ItemDTO(2,"Проооод", 1, 1, 2),
        ItemDTO(32,"Вайбы", 77, 1, 2))
    val cart = mutableListOf(SkuDTO(14,"Хлеб", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 5, 1),
        SkuDTO(4,"Рофлы", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 1, 5),
        SkuDTO(6,"Ярослав", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 1, 8),
        SkuDTO(777,"Вайбы", "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png", 77, 1))
    var accounts = listOf<UserDTO>(
        UserDTO(0, "a", "Asdasd", "Asdasd", 0),
        UserDTO(1, "b", "mmmmmm", "Asdasd", 0),
    )
    var holodoss = listOf<HolodosDTO>(
        HolodosDTO(2, "Прикольный Холодос")
    )
    var userHolodos = mapOf(
        0L to 2L,
        1L to 2L,
        32L to 2L
    )
//
//    override fun getQrData(qr: String): Call<QRDTO> {
//        TODO("Not yet implemented")
//    }
//
//    override fun createUser(createUserDTO: CreateUserDTO) {
//        TODO("Not yet implemented")
//    }
//
//    override fun changeUserAvatar(avatarIndex: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getUserByPhone(phone: String): Call<UserDTO> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getUserById(id: Long): Call<UserDTO> {
//        TODO("Not yet implemented")
//    }
//
//    override fun createProduct(userId: Long, holodosId: Long, data: ItemDTO): Call<ItemDTO> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getProducts(userId: Long, holodosId: Long): Call<List<ItemDTO>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun createHolodos(data: HolodosDTO): Call<HolodosDTO> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getHolodosByUserId(userId: Long): Call<List<HolodosResponse>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun searchInCart(query: String): Call<List<Sku>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getCart(userId: Long, holodosId: Long): Call<List<SkuDTO>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun updateQuantity(userId: Long, holodosId: Long, skuId: Long, quantity: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun addToCart(userId: Long, holodosId: Long, skuId: Long) {
//        TODO("Not yet implemented")
//    }
//
//    override fun serverStatus(): Call<ResponseBody> {
//        val retrofit = Retrofit.Builder().baseUrl(C.BASE_URL).build()
//        return retrofit.create(HolodosService::class.java).serverStatus()
//    }
//
//    override suspend fun getProductsByHolodos(id: Long): List<ItemDTO> {
//        return items.filter { it.holodosId == id }
//    }
//
//    override suspend fun deleteProductFromHolodos(id: Long) {
//        items.removeAll { it.id == id }
//    }
//
//    override suspend fun addProductsToHolodos(data: ItemDTO) {
//        items.add(data)
//    }
//
//    override suspend fun login(number: String): Long {
//        return 0
//    }
//
//    override suspend fun addUser(data: UserDTO) {
//        accounts = accounts.plus(data)
//    }
//
//    override suspend fun getUserById(id: Long): UserDTO {
//        return accounts.find { it.id == id }!!
//    }
//
//    override suspend fun getUserByPhone(phone: String): UserDTO {
//        return accounts.find { it.phone == phone }!!
//    }
//
//    override suspend fun updateUser(user: UserDTO) {
//        val u = accounts.indexOfFirst { it.id == user.id }
//        val mut = accounts.toMutableList()
//        mut[u] = user
//        accounts = mut
//    }
//
//    override suspend fun getHolodosByUserId(id: Long): HolodosDTO {
//        return holodoss.find { it.id == userHolodos[id] }!!
//    }
//
//    override suspend fun getHolodosGroup(id: Long): GroupDTO {
//        return GroupDTO(userHolodos.filter { it.value == id }.keys.toList().map { kid -> accounts.find { it.id == kid }!! })
//    }
//
//    override suspend fun getCartItems(): List<SkuDTO> {
//        return cart
//    }
//
//    override suspend fun updateProductInHolodos(id: Long, count: Int) {
//        val ind = items.indexOfFirst { it.id == id }
//        items[ind] = items[ind].copy(quantity = count)
//    }
//
//    override fun getQrData(qr: String): Call<QRDTO> {
//        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(C.BASE_URL).build().create(HolodosService::class.java).getQrData(qr)
//    }
//
//    override suspend fun getUsers(id: Long): List<UserDTO> {
//        return accounts
//    }
}