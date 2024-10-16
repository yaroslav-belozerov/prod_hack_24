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
//    @POST("/holodos/api/updateUser/{id}")
//    suspend fun updateUser(user: UserDTO)
//
//    @GET("/holodos/api/getHolodosGroup/{id}")
//    suspend fun getHolodosGroup(@Path("id") id: Long): GroupDTO
//
//    @GET("/holodos/api/status")
//    fun serverStatus(): Call<ResponseBody>
//
//    @GET("/holodos/api/getProductsByHolodos/{id}")
//    suspend fun getProductsByHolodos(@Path("id") id: Long): List<ItemDTO>
//
//    @GET("/holodos/api/deleteProductFromHolodos/{id}")
//    suspend fun deleteProductFromHolodos(@Path("id") id: Long)
//
//    @POST("/holodos/api/addProductToHolodos")
//    suspend fun addProductsToHolodos(data: ItemDTO)
//
//    @GET("/holodos/api/login")
//    suspend fun login(number: String): Long
//
//    @GET("/holodos/api/addUser")
//    suspend fun addUser(data: UserDTO)
//
//    @GET("/holodos/api/users/{id}")
//    suspend fun getUsers(@Path("id") id: Long): List<UserDTO>
//
//    @GET("/holodos/api/cartitems")
//    suspend fun getCartItems(): List<SkuDTO>
//
//    @GET("TODO")
//    suspend fun updateProductInHolodos(id: Long, count: Int)
    @PUT("/holodos/api/user/")
    fun putUser(@Body user: CreateUserDTO): Call<ResponseBody>

    @POST("/holodos/api/receipt")
    fun getQrData(@Body qr: Data): Call<QRDTO>

    @POST("/holodos/api/user/")
    fun createUser(@Body createCreateCreateUserD: CreateUserDTO): Call<CreateUserDTO>

    @POST("/holodos/api/user/changeAvatar/")
    fun changeUserAvatar(@Query("avatar") avatarIndex: Int): Call<ResponseBody>

    @GET("/holodos/api/user/phone")
    fun getUserByPhone(@Query("phone") phone: String): Call<CreateUserDTO>

    @GET("/holodos/api/user/{id}")
    fun getUserById(@Path("id") id: Long): Call<CreateUserDTO>

    @POST("/holodos/api/products/")
    fun createProduct(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long, @Body data: CreateProductDTO): Call<CreateProductDTO>

    @GET("/holodos/api/products/products")
    fun getProducts(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long): Call<List<CreateProductDTO>>

    @POST("/holodos/api/holodos/")
    fun createHolodos(@Body data: HolodosResponse): Call<HolodosResponse>

    @POST("/holodos/api/holodos/{id}/users")
    fun addUserToHolodos(@Path("id") id: Long, @Query("userId") userId: Long): Call<HolodosResponse>

    @GET("/holodos/api/holodos/userId")
    fun getHolodosByUserId(@Query("userId") userId: Long): Call<List<HolodosResponse>>

    @GET("/holodos/api/shoppingCart/search")
    fun searchInCart(@Query("query") query: String): Call<List<Sku>>

    @GET("/holodos/api/shoppingCart/get")
    fun getCart(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long): Call<List<SkuDTO>>

    @PUT("/holodos/api/shoppingCart/updateQuantity")
    fun updateQuantity(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long, @Query("skuId") skuId: Long, @Query("quantity") quantity: Int): Call<ResponseBody>

    @POST("/holodos/api/shoppingCart/add")
    fun addToCart(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long, @Query("skuId") skuId: Long):Call<ResponseBody>

    @DELETE("/product/")
    fun deleteProductById(@Query("id") id: Long): Call<ResponseBody>
}