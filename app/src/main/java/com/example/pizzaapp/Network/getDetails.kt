package com.example.pizzaapp.Network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class getDetails (
    // bevat de data voor een product dat we naar de API willen sturen
    @SerialName(value = "BestellingId") val BestellingId: Int,
)