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

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzaapp.R
import com.example.pizzaapp.model.Pizza
import com.example.pizzaapp.ui.theme.MarsPhotosTheme
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.request.ImageRequest
import coil.compose.AsyncImage
import com.example.pizzaapp.model.ShoppingCartLine

var shoppingCartList = mutableListOf<ShoppingCartLine>()
var pizzaList = mutableListOf<Pizza>()

@Composable
fun MenuList(
    pizzas: List<Pizza>,
    modifier: Modifier = Modifier,
    onCartButtonClick: (List<ShoppingCartLine>) -> Unit,
    onAccountButtonClick: ()->Unit,
    pizzaViewModel: PizzaViewModel
) {
    val pizzaList = pizzas.toMutableList()
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Row {
                    IconButton(
                        onClick = { onCartButtonClick(shoppingCartList) },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = "Cart"
                        )
                    }
                    IconButton(
                        onClick = { onAccountButtonClick() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Account"
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn() {
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
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
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
                                    TextButton(onClick = { if (quantity > 0) quantity-- }) {
                                        Text("-")
                                    }
                                    TextField(
                                        value = quantity.toString(),
                                        onValueChange = { newValue ->
                                            if (newValue > 0.toString()) quantity =
                                                newValue.toIntOrNull() ?: quantity
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
                                            val shoppingCartLine = ShoppingCartLine(
                                                id = shoppingCartList.size + 1,
                                                pizza = pizzas[index],
                                                quantity = quantity
                                            )

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

                Button(
                    onClick = { onCartButtonClick(shoppingCartList) },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("View Cart")
                }
            }
        }
    }
}



/*
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
*/