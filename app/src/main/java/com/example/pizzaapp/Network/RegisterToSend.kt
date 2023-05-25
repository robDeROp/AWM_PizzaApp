package com.example.pizzaapp.Network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RegisterToSend (
    @SerialName(value = "FirstName") val firstName: String,
    @SerialName(value = "LastName") val lastName: String,
    @SerialName(value = "Email") val email: String,
    @SerialName(value = "Password") val password: String,
    @SerialName(value = "PhoneNr") val phoneNr: String,
    @SerialName(value = "Role") val role: Int
)