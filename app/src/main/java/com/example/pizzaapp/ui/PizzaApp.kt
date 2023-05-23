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

package com.example.pizzaapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marsphotos.ui.screens.HomeScreen
import com.example.pizzaapp.R
import com.example.pizzaapp.ui.screens.PizzaUiState
import com.example.pizzaapp.ui.screens.PizzaViewModel
enum class Screen {
    Home,
    Login,
    Shopping
}
@Composable
fun PizzaBestelApp(modifier: Modifier = Modifier) {
    val PizzaViewModel: PizzaViewModel = viewModel()

    var currentScreen by remember { mutableStateOf(Screen.Home) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(
                        onClick = { currentScreen = Screen.Shopping },
                        content = {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Shopping Cart"
                            )
                        }
                    )
                    IconButton(
                        onClick = { currentScreen = Screen.Login },
                        content = {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Login"
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            currentScreen = Screen.Home
                            PizzaViewModel.getPizzas()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        }
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val PizzaViewModel: PizzaViewModel = viewModel()
                if (currentScreen == Screen.Home) {
                    HomeScreen(pizzaUiState = PizzaViewModel.pizzaUiState)
                } else if (currentScreen == Screen.Login) {
                    HomeScreen(pizzaUiState = PizzaUiState.Login)
                }
                else if (currentScreen == Screen.Shopping) {
                    HomeScreen(pizzaUiState = PizzaUiState.ShoppingCart)
                }
            }
        },

    )
}
