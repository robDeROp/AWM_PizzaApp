package com.example.pizzaapp.Network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class getDetailsResponse (
    @SerialName(value = "BestellingLineId") val BestellingLineId: Int,
    @SerialName(value = "PizzaId") val PizzaId: Int,
    @SerialName(value = "BestellingId") val BestellingId: Int,
    @SerialName(value = "Quantity") val Quantity: Int,
)