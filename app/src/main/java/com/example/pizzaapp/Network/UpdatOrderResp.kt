package com.example.pizzaapp.Network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UpdatOrderResp (
    // bevat de data voor een product dat we naar de API willen sturen
    @SerialName(value = "data") val data: String,
    @SerialName(value = "message") val message: String,
    @SerialName(value = "status") val status: String
)