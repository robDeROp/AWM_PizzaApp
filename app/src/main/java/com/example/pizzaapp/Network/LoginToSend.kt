package com.example.pizzaapp.Network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoginToSend (
    // bevat de data voor een product dat we naar de API willen sturen
    @SerialName(value = "Email") val email: String,
    @SerialName(value = "Password") val password: String,

)