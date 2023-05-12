package com.example.pizzaapp.Network

import com.example.pizzaapp.model.Pizza
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseProductsGet(
    @SerialName("data") val data: List<Pizza>,
    @SerialName(value = "status") val status: String? = null,
    @SerialName(value = "code") val code: String? = null
)