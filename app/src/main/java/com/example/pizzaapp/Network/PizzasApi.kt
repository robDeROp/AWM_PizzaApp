package com.example.pizzaapp.Network
import com.example.pizzaapp.model.CurrentUser
import com.example.pizzaapp.model.OrderLine
import com.example.pizzaapp.model.Pizza

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


private const val BASE_URL = "https://www.quintenstroobants.be/pizzastoreapi/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

object PizzaApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {

    /* het /PRODUCTSget.php endpoint */
    @GET("getpizzas.php")
    suspend fun getProducten(): List<Pizza>
    @GET("getorders.php")
    suspend fun getOrders(): List<OrderLine>

    @GET("getorderline.php")
    suspend fun getLines(
        @Query("productData") productData: getDetails
    ): List<getDetailsResponse>
    @POST("checklogin.php")
    suspend fun checkLogin(@Body productData: LoginToSend): List<CurrentUser>

    @POST("adduser.php")
    suspend fun checkRegister(@Body productData: RegisterToSend): CurrentUser

    @POST("addorder.php")
    suspend fun AddOrder(@Body productData: PostOrder): AddOrderResp
    @POST("updateorderstatus.php")
    suspend fun UpdateOrder(@Body productData: UpdateOrder): UpdatOrderResp
    @POST("addorderline.php")
    suspend fun AddLine(@Body productData: PostOrderLine): AddOrderLineResp
}