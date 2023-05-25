package com.example.pizzaapp.Network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PostOrder (
    // bevat de data voor een product dat we naar de API willen sturen
    @SerialName(value = "UserId") val userId: Int,
    @SerialName(value = "Comment") val Comment: String,
    @SerialName(value = "PickupTime") val PickupTime: String,
    @SerialName(value = "Status") val Status: Int,
)