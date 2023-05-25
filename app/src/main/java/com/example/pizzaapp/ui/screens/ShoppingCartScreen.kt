package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzaapp.model.CurrentUser
import com.example.pizzaapp.model.ShoppingCartLine

@Composable
fun ShoppingCartScreen(
    shoppingCartLines: MutableList<ShoppingCartLine>,
    modifier: Modifier = Modifier,
    onBackHomeClick: () -> Unit,
    onPlaceOrder: () -> Unit,
    toLogin: () -> Unit,
    pizzaViewModel: PizzaViewModel
) {
    val cartLinesState = remember { mutableStateListOf(*shoppingCartLines.toTypedArray()) }
    Text(
        text = "Shopping Cart",
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(vertical = 16.dp)
    )
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Shopping Cart",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 16.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackHomeClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                if (cartLinesState.isEmpty()) {
                    Text(
                        text = "Je winkelwagen is leeg",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = { onBackHomeClick() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Bekijk de menu")
                    }
                } else {
                    for (line in cartLinesState) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = line.pizza.name,
                                modifier = Modifier.weight(1f),
                                fontSize = 18.sp // Increased text size
                            )
                            Text(
                                text = "Quantity: ${line.quantity}",
                                modifier = Modifier.width(80.dp),
                                fontSize = 16.sp // Increased text size
                            )
                            Text(
                                text = "Total: ${
                                    String.format(
                                        "%.2f",
                                        line.quantity * line.pizza.price
                                    )
                                }€",
                                modifier = Modifier.width(100.dp),
                                fontSize = 16.sp // Increased text size
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
                Button(
                    onClick = {
                        if(pizzaViewModel.currentUserRole == -1){
                            toLogin()
                        }
                        else{
                            onPlaceOrder()
                        }
                     },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Order Plaatsen")
                }
            }
        }
    }
}
