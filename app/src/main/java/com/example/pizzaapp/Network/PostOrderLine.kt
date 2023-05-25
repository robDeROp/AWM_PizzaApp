package com.example.pizzaapp.Network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PostOrderLine (
    @SerialName(value = "PizzaId") val PizzaId: Int,
    @SerialName(value = "BestellingId") val BestellingId: Int,
    @SerialName(value = "Quantity") val Quantity: Int,
)