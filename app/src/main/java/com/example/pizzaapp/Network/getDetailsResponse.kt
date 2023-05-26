package com.example.pizzaapp.Network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class getDetailsResponse (
    @SerialName(value = "BestellingLineId") val BestellingLineId: Int,
    @SerialName(value = "Name") val PizzaName: String,
    @SerialName(value = "Quantity") val Quantity: Int,
    @SerialName(value = "Price") val Price: Double,
)