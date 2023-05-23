/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.pizzaapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.Network.LoginToSend
import com.example.pizzaapp.Network.ProductenApi
import com.example.pizzaapp.model.CurrentUser
import com.example.pizzaapp.model.Pizza
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface PizzaUiState {
    data class Menu(val pizzas: List<Pizza>) : PizzaUiState
    object Error : PizzaUiState
    object Loading : PizzaUiState
    object Login : PizzaUiState
    object Register : PizzaUiState
    object ShoppingCart : PizzaUiState
    data class LoginSuccess(val user: CurrentUser) : PizzaUiState}

class PizzaViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var pizzaUiState: PizzaUiState by mutableStateOf(PizzaUiState.Loading)
        private set
    // Producten ophalen
    lateinit var producten: List<Pizza>
        private set
    // Product toevoegen


    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getPizzas()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     */

    private fun getOrders() {
        viewModelScope.launch {
            pizzaUiState = try {
                val response = ProductenApi.retrofitService.getProducten()
                // TODO: hou rekening met response.status en response.message zodra die zijn
                // toegevoegd.
                producten = response
                PizzaUiState.Menu(producten)
            } catch(e: IOException) {
                PizzaUiState.Error
            }
        }
    }

    fun getPizzas() {
        viewModelScope.launch {
                val response = ProductenApi.retrofitService.getProducten()
                // TODO: hou rekening met response.status en response.message zodra die zijn
                // toegevoegd.
                producten = response
        }
    }
    fun ToRegister (){
    }
    fun LogOut() {
        pizzaUiState = PizzaUiState.Menu(producten)
    }
    fun tryLogin(email: String, password: String) {
        pizzaUiState = PizzaUiState.Loading

        val nieuwProduct = LoginToSend(
            email = email,
            password = password
        )

        viewModelScope.launch {
            try {
                val response = ProductenApi.retrofitService.checkLogin(nieuwProduct)
                if (response.isNotEmpty()) {
                    // Login successful, response contains user details
                    val currentUser = response[0]
                    pizzaUiState = PizzaUiState.LoginSuccess(currentUser)
                } else {
                    // Login failed, response is empty
                    pizzaUiState = PizzaUiState.Error
                }
            } catch (e: IOException) {
                pizzaUiState = PizzaUiState.Error
            }
        }
    }
}
