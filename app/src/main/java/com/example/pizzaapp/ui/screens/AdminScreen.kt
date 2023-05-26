package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pizzaapp.R
import com.example.pizzaapp.model.ShoppingCartLine

@Composable
fun AdminScreen(modifier: Modifier = Modifier,
                ToDetails: () -> Unit,
                onLogoutButtonClick: () -> Unit,
                onAccountButtonClick: () -> Unit,
                pizzaViewModel: PizzaViewModel,
) {
    pizzaViewModel.getOrders()
    val orders = pizzaViewModel.orders.toMutableList()
    Column {
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
                        onClick = { onLogoutButtonClick() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Filled.ExitToApp,
                            contentDescription = "Exit"
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
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn() {
                    //Filter toevoegen

                    items(orders.filter { it.Status == 0 }.size) { index ->
                        val order = orders.filter { it.Status == 0 }[index]

                        Column {
                            Text(text = "Order ID: ${order.BestellingId}")
                            Text(text = "User ID: ${order.UserId}")
                            Text(text = "Comment: ${order.Comment}")
                            Text(text = "Pickup Time: ${order.PickupTime}")
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Button(
                                    onClick = { pizzaViewModel.updateOrder(order.BestellingId, 1) },
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(text = "DONE")
                                }
                                Button(
                                    onClick = { pizzaViewModel.updateOrder(order.BestellingId, -1) },
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(text = "CANCEL")
                                }
                                Button(
                                    onClick = {
                                        pizzaViewModel.getLines(order.BestellingId)
                                        ToDetails()
                                              },
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(text = "DETAILS")
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
    }
}
