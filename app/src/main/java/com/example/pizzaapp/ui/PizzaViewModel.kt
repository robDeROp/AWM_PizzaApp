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

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.Network.*
import com.example.pizzaapp.model.CurrentUser
import com.example.pizzaapp.model.OrderLine
import com.example.pizzaapp.model.Pizza
import com.example.pizzaapp.model.ShoppingCartLine
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

sealed interface LoginUIState {
    data class Success(val data: CurrentUser) : LoginUIState
    object Error: LoginUIState
    object Loading: LoginUIState
    object Empty : LoginUIState // New state added
}
sealed interface OrderUIState {
    data class Success(val ID: Int): OrderUIState
    object Error: OrderUIState
    object Loading: OrderUIState
    object Empty : OrderUIState // New state added
}

class PizzaViewModel : ViewModel() {
    var pizzas by mutableStateOf<List<Pizza>>(emptyList())
        private set

    var orders by mutableStateOf<List<OrderLine>>(emptyList())
        private set

    var orderDetails by mutableStateOf<List<getDetailsResponse>>(emptyList())
        private set

    var loginUIState: LoginUIState by mutableStateOf(LoginUIState.Empty)
        private set

    var orderUIState: OrderUIState by mutableStateOf(OrderUIState.Empty)
        private set

    var currentUser: CurrentUser? = null
    var currentUserRole: Int? = -1

    var shoppingCartList = mutableListOf<ShoppingCartLine>()
    init {
        getPizzas()
    }
    fun ResetUIState(){
        loginUIState = LoginUIState.Empty
    }
    fun ResetUIOrderState(){
        orderUIState = OrderUIState.Empty
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun PlaceYourOrder(hours:Int, minutes:Int, comment:String){

        val now = LocalDateTime.now()
        val updatedDateTime = now.withHour(hours).withMinute(minutes)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
        val updatedDateTimeString = updatedDateTime.format(formatter)

        val loginCred = PostOrder(
            userId = currentUser!!.id,
            Comment = comment,
            PickupTime = updatedDateTimeString,
            Status = 0
        )
        viewModelScope.launch {
            orderUIState = try {
                OrderUIState.Loading

                val response = PizzaApi.retrofitService.AddOrder(loginCred)
                // TODO: Take into account response.status and response.message once they are added.
                if (response.OID != null) {
                    shoppingCartList.forEach { ShoppingCartLine ->
                        val loginCred = PostOrderLine(
                            PizzaId = ShoppingCartLine.pizza.id,
                            BestellingId = response.OID,
                            Quantity = ShoppingCartLine.quantity,
                        )
                        val innerResponse = PizzaApi.retrofitService.AddLine(loginCred)
                    }
                    OrderUIState.Success(response.OID) // Assign the appropriate value of LoginUIState

                    //OrderUIState.Success(response.OID, shoppingCartList, updatedDateTimeString) // Assign the appropriate value of LoginUIState
                } else {
                    OrderUIState.Error
                }
            } catch (e: IOException) {
                OrderUIState.Error
            }
        }
    }
    fun loginUser(email: String, password: String) {
        val loginCred = LoginToSend(
            email = email,
            password = password
        )
        viewModelScope.launch {
            loginUIState = try {
                LoginUIState.Loading

                val response = PizzaApi.retrofitService.checkLogin(loginCred)
                // TODO: hou rekening met response.status en response.message zodra die zijn
                // toegevoegd.
                if(response.isNotEmpty()){
                    currentUserRole = response[0].R
                    currentUser = response[0]
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
    fun registerUser(firstName: String, lastName: String, email: String, password: String, Phone: String) {
        val loginCred = RegisterToSend(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            phoneNr = Phone,
            role = 0
        )
        viewModelScope.launch {
            loginUIState = try {
                LoginUIState.Loading

                val response = PizzaApi.retrofitService.checkRegister(loginCred)
                // TODO: hou rekening met response.status en response.message zodra die zijn
                // toegevoegd.
                if(response != null){
                    currentUserRole = response.R
                    currentUser = response
                    LoginUIState.Success(response)
                }
                else{
                    LoginUIState.Error
                }
            } catch (e: IOException) {
                LoginUIState.Error
            }
        }
    }
    fun getOrders() {
        viewModelScope.launch {
            try {
                val response = PizzaApi.retrofitService.getOrders()
                val Orders = response
                if (Orders != null) {
                    orders = Orders
                } else {
                    // Handle null response body
                }
            } catch (e: IOException) {
                // Handle network error
            }
        }
    }
    fun getLines(OrderID: Int) {
        viewModelScope.launch {
            val loginCred = getDetails(
                BestellingId = OrderID
            )
            try {
                val response = PizzaApi.retrofitService.getLines(loginCred)
                val OrdersDetails = response
                if (OrdersDetails != null) {
                     orderDetails = OrdersDetails
                } else {
                    // Handle null response body
                }
            } catch (e: IOException) {
                // Handle network error
            }
        }
    }
    fun updateOrder (OrderID: Int) {
        val loginCred = UpdateOrder(
            BestellingId = OrderID,
            Status = 1
        )
        viewModelScope.launch {
            val response = PizzaApi.retrofitService.UpdateOrder(loginCred)
            // TODO: hou rekening met response.status en response.message zodra die zijn
            // toegevoegd.
            getOrders()
        }
    }
    private fun getPizzas() {
        viewModelScope.launch {
            try {
                val response = PizzaApi.retrofitService.getProducten()
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
