package com.yaabelozerov.holodos_mobile.domain.network

import com.yaabelozerov.holodos_mobile.data.CreateProductDTO
import com.yaabelozerov.holodos_mobile.data.CreateUserDTO
import com.yaabelozerov.holodos_mobile.data.GroupDTO
import com.yaabelozerov.holodos_mobile.data.HolodosDTO
import com.yaabelozerov.holodos_mobile.data.HolodosResponse
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.QRDTO
import com.yaabelozerov.holodos_mobile.data.Sku
import com.yaabelozerov.holodos_mobile.data.SkuDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

data class Data(
    val data: String
)

interface HolodosService {
//    @POST("/api/updateUser/{id}")
//    suspend fun updateUser(user: UserDTO)
//
//    @GET("/api/getHolodosGroup/{id}")
//    suspend fun getHolodosGroup(@Path("id") id: Long): GroupDTO
//
//    @GET("/api/status")
//    fun serverStatus(): Call<ResponseBody>
//
//    @GET("/api/getProductsByHolodos/{id}")
//    suspend fun getProductsByHolodos(@Path("id") id: Long): List<ItemDTO>
//
//    @GET("/api/deleteProductFromHolodos/{id}")
//    suspend fun deleteProductFromHolodos(@Path("id") id: Long)
//
//    @POST("/api/addProductToHolodos")
//    suspend fun addProductsToHolodos(data: ItemDTO)
//
//    @GET("/api/login")
//    suspend fun login(number: String): Long
//
//    @GET("/api/addUser")
//    suspend fun addUser(data: UserDTO)
//
//    @GET("/api/users/{id}")
//    suspend fun getUsers(@Path("id") id: Long): List<UserDTO>
//
//    @GET("/api/cartitems")
//    suspend fun getCartItems(): List<SkuDTO>
//
//    @GET("TODO")
//    suspend fun updateProductInHolodos(id: Long, count: Int)
    @PUT("/api/user/")
    fun putUser(@Body user: CreateUserDTO): Call<ResponseBody>

    @POST("/api/receipt")
    fun getQrData(@Body qr: Data): Call<QRDTO>

    @POST("/api/user/")
    fun createUser(@Body createCreateCreateUserD: CreateUserDTO): Call<CreateUserDTO>

    @POST("/api/user/changeAvatar/")
    fun changeUserAvatar(@Query("avatar") avatarIndex: Int): Call<ResponseBody>

    @GET("/api/user/phone")
    fun getUserByPhone(@Query("phone") phone: String): Call<CreateUserDTO>

    @GET("/api/user/{id}")
    fun getUserById(@Path("id") id: Long): Call<CreateUserDTO>

    @POST("/api/products/")
    fun createProduct(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long, @Body data: CreateProductDTO): Call<CreateProductDTO>

    @GET("/api/products/products")
    fun getProducts(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long): Call<List<CreateProductDTO>>

    @POST("/api/holodos/")
    fun createHolodos(@Body data: HolodosResponse): Call<HolodosResponse>

    @POST("/api/holodos/{id}/users")
    fun addUserToHolodos(@Path("id") id: Long, @Query("userId") userId: Long): Call<HolodosResponse>

    @GET("/api/holodos/userId")
    fun getHolodosByUserId(@Query("userId") userId: Long): Call<List<HolodosResponse>>

    @GET("/api/shoppingCart/search")
    fun searchInCart(@Query("query") query: String): Call<List<Sku>>

    @GET("/api/shoppingCart/get")
    fun getCart(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long): Call<List<SkuDTO>>

    @PUT("/api/shoppingCart/updateQuantity")
    fun updateQuantity(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long, @Query("skuId") skuId: Long, @Query("quantity") quantity: Int): Call<ResponseBody>

    @POST("/api/shoppingCart/add")
    fun addToCart(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long, @Query("skuId") skuId: Long):Call<ResponseBody>

    @DELETE("/product/")
    fun deleteProductById(@Query("id") id: Long): Call<ResponseBody>
}