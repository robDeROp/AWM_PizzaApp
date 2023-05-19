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
package com.example.marsphotos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzaapp.R
import com.example.pizzaapp.model.Pizza
import com.example.pizzaapp.ui.screens.MarsUiState
import com.example.pizzaapp.ui.theme.MarsPhotosTheme
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.request.ImageRequest
import coil.compose.AsyncImage
import com.example.pizzaapp.Network.ApiService
import com.example.pizzaapp.model.ShoppingCartLine
import com.example.pizzaapp.ui.screens.MarsViewModel

val shoppingCartList = mutableListOf<ShoppingCartLine>()
var pizzaList = mutableListOf<Pizza>()
@Composable
fun HomeScreen(
    marsUiState: MarsUiState,
    modifier: Modifier = Modifier
) {
    when (marsUiState) {
        is MarsUiState.Loading -> LoadingScreen(modifier)
        is MarsUiState.Success -> MenuList(marsUiState.pizzas, modifier)
        is MarsUiState.Error -> ErrorScreen(modifier)
        is MarsUiState.Login -> LoginScreen(modifier)
        is MarsUiState.ShoppingCart -> {
            ShoppingCartScreen(shoppingCartList, modifier)
        }
    }
}

/**
 * The home screen displaying the loading message.
 */


@Composable
fun ShoppingCartScreen(shoppingCartLines: MutableList<ShoppingCartLine>, modifier: Modifier = Modifier) {
    val cartLinesState = remember { mutableStateListOf(*shoppingCartLines.toTypedArray()) }
    Text(
        text = "Shopping Cart",
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(vertical = 16.dp)
    )
    Column {
        for (line in cartLinesState) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = line.pizza.name,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Quantity: ${line.quantity}",
                    modifier = Modifier.width(80.dp)
                )
                Text(
                    text = "Total: ${String.format("%.2f", line.quantity * line.pizza.price)}€",
                    modifier = Modifier.width(100.dp)
                )
                IconButton(
                    onClick = {
                        cartLinesState.remove(line)
                        shoppingCartLines.remove(line)
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }
    }
}


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val marsViewModel: MarsViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoginEnabled = email.isNotBlank() && password.isNotBlank()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { marsViewModel.tryLogin(email, password, marsViewModel) },
                enabled = isLoginEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login")
            }
        }
    }
}
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

/**
 * The home screen displaying error message
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
    }
}

/**
 * The home screen displaying number of retrieved photos.
 */
@Composable
fun MenuList(pizzas: List<Pizza>, modifier: Modifier = Modifier) {
    pizzaList = pizzas as MutableList<Pizza>
    Text(
        text = "Menu",
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(vertical = 16.dp)
    )
    LazyColumn(modifier.fillMaxSize().padding(top = 16.dp)) {
        items(pizzas.size) { index ->
            Column {
                // Display the pizza image at the top
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(pizzas[index].photourl)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.mars_photo),
                        contentScale = ContentScale.FillWidth,
                    )
                }

                // Display the pizza name, ingredients, and price below the image
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = pizzas[index].name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = pizzas[index].ingredients,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "€${pizzas[index].price}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Quantity and Add to Cart button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var quantity by remember { mutableStateOf(0) }
                        TextButton(onClick = { if(quantity>0) quantity-- }) {
                            Text("-")
                        }
                        TextField(
                            value = quantity.toString(),
                            onValueChange = { newValue ->
                                if(newValue> 0.toString())quantity = newValue.toIntOrNull() ?: quantity
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.height(50.dp).width(80.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        TextButton(onClick = { quantity++ }) {
                            Text("+")
                        }
                        Button(
                            onClick = {
                                val shoppingCartLine = ShoppingCartLine(id = shoppingCartList.size + 1, pizza = pizzas[index], quantity = quantity)

                                if (shoppingCartLine.quantity > 0) {
                                    var lineExists = false

                                    for (line in shoppingCartList) {
                                        if (line.pizza == shoppingCartLine.pizza) {
                                            line.quantity += shoppingCartLine.quantity
                                            lineExists = true
                                            break
                                        }
                                    }

                                    if (!lineExists) {
                                        shoppingCartList.add(shoppingCartLine)
                                    }
                                }
                            },
                            modifier = Modifier.height(50.dp).width(200.dp)
                        ) {
                            Text("Add to cart")
                        }
                    }
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    MarsPhotosTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    MarsPhotosTheme {
        ErrorScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    val pizzas = listOf(
        Pizza(1,"Pizza Margherita", 12.3, "","Fresh tomato sauce, mozzarella, and basil"),
        Pizza(1,"Pizza Margherita", 12.3, "","Fresh tomato sauce, mozzarella, and basil"),
        Pizza(1,"Pizza Margherita", 12.3, "","Fresh tomato sauce, mozzarella, and basil"),
        Pizza(1,"Pizza Margherita", 12.3, "","Fresh tomato sauce, mozzarella, and basil"),
        Pizza(1,"Pizza Margherita", 12.3, "","Fresh tomato sauce, mozzarella, and basil"),
        Pizza(1,"Pizza Margherita", 12.3, "","Fresh tomato sauce, mozzarella, and basil"),
        Pizza(1,"Pizza Margherita", 12.3, "","Fresh tomato sauce, mozzarella, and basil"),
        Pizza(1,"Pizza Margherita", 12.3, "","Fresh tomato sauce, mozzarella, and basil"),
    )
    MarsPhotosTheme {
        MenuList(pizzas)
    }
}
