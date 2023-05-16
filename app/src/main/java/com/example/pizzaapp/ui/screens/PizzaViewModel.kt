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
import com.example.pizzaapp.Network.ProductenApi
import com.example.pizzaapp.model.Pizza
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface MarsUiState {
    data class Success(val pizzas: List<Pizza>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
    object Login : MarsUiState
    object ShoppingCart : MarsUiState

}

class MarsViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
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
    private fun getPizzas() {
        viewModelScope.launch {
            marsUiState = try {
                val response = ProductenApi.retrofitService.getProducten()
                // TODO: hou rekening met response.status en response.message zodra die zijn
                // toegevoegd.
                producten = response
                MarsUiState.Success(producten)
            } catch(e: IOException) {
                MarsUiState.Error
            }
        }
    }

}
