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
sealed interface LoginUIState {
    data class Success(val data: CurrentUser) : LoginUIState
    object Error: LoginUIState
    object Loading: LoginUIState
    object Empty : LoginUIState // New state added
}

class PizzaViewModel : ViewModel() {
    var pizzas by mutableStateOf<List<Pizza>>(emptyList())
        private set


    var loginUIState: LoginUIState by mutableStateOf(LoginUIState.Empty)
        private set

    var UserID: Int = 0
    init {
        getPizzas()
    }
    fun loginUser(email: String, password: String) {
        val loginCred = LoginToSend(
            email = email,
            password = password
        )
        viewModelScope.launch {
            loginUIState = try {
                LoginUIState.Loading

                val response = ProductenApi.retrofitService.checkLogin(loginCred)
                // TODO: hou rekening met response.status en response.message zodra die zijn
                // toegevoegd.
                if(response.isNotEmpty()){
                    UserID = response[0].id
                    LoginUIState.Success(response[0])
                }
                else{
                    LoginUIState.Error
                }
            } catch (e: IOException) {
                LoginUIState.Error
            }
        }
    }
    private fun getPizzas() {
        viewModelScope.launch {
            try {
                val response = ProductenApi.retrofitService.getProducten()
                    val pizzaList = response
                    if (pizzaList != null) {
                        pizzas = pizzaList
                    } else {
                        // Handle null response body
                    }
            } catch (e: IOException) {
                // Handle network error
            }
        }
    }
}
