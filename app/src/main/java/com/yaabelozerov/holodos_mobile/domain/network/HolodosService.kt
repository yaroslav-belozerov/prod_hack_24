package com.yaabelozerov.holodos_mobile.domain.network

import android.util.Log
import com.yaabelozerov.holodos_mobile.data.CreateProductDTO
import com.yaabelozerov.holodos_mobile.data.CreateUserDTO
import com.yaabelozerov.holodos_mobile.data.GroupDTO
import com.yaabelozerov.holodos_mobile.data.HolodosDTO
import com.yaabelozerov.holodos_mobile.data.HolodosResponse
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.QRDTO
import com.yaabelozerov.holodos_mobile.data.Sku
import com.yaabelozerov.holodos_mobile.data.SkuDTO
import okhttp3.Interceptor
import okhttp3.Response
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

class LogInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code() == 200) {
            Log.i("Request", "OK ${request.method()} ${request.url()}}")
        } else {
            Log.e("Request", "${response.code()} ${request.method()} ${request.url()}")
        }
        return response
    }
}

interface HolodosService {
    @PUT("api/user/")
    fun putUser(@Body user: CreateUserDTO): Call<ResponseBody>

    @POST("api/receipt")
    fun getQrData(@Body qr: Data): Call<ResponseBody>

    @POST("api/user/")
    fun createUser(@Body createCreateCreateUserD: CreateUserDTO): Call<CreateUserDTO>

    @GET("api/user/phone")
    fun getUserByPhone(@Query("phone") phone: String): Call<CreateUserDTO>

    @GET("api/user/{id}")
    fun getUserById(@Path("id") id: Long): Call<CreateUserDTO>

    @POST("api/products/")
    fun createProduct(@Query("holodosId") holodosId: Long, @Body data: CreateProductDTO): Call<CreateProductDTO>

    @GET("api/products/products")
    fun getProducts(@Query("userId") userId: Long, @Query("holodosId") holodosId: Long): Call<List<CreateProductDTO>>

    @POST("api/holodos/")
    fun createHolodos(@Body data: HolodosResponse): Call<HolodosResponse>

    @POST("api/holodos/{id}/users")
    fun addUserToHolodos(@Path("id") id: Long, @Query("userId") userId: Long): Call<Unit>

    @GET("api/holodos/userId")
    fun getHolodosByUserId(@Query("userId") userId: Long): Call<List<HolodosResponse>>

    @GET("api/holodos/{id}/users")
    fun getHolodosUsers(@Path("id") id: Long): Call<List<CreateUserDTO>>

    @DELETE("api/products/")
    fun deleteProductById(@Query("id") id: Long): Call<ResponseBody>
}