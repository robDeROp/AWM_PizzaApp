package com.example.pizzaapp.Network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class getUserOrders (
    // bevat de data voor een product dat we naar de API willen sturen
    @SerialName(value = "UserId") val UserId: Int,
)