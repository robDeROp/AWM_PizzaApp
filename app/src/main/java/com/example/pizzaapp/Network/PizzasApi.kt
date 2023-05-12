package com.example.pizzaapp.Network
import com.example.pizzaapp.model.Pizza

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


private const val BASE_URL = "https://www.quintenstroobants.be/pizzastoreapi/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

object ProductenApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {

    /* het /PRODUCTSget.php endpoint */
    @GET("getpizzas.php")
    suspend fun getProducten(): List<Pizza>
}