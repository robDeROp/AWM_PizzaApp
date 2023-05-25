package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ShoppingCart
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
                onBackHomeClick: () -> Unit,
                onLogoutButtonClick: () -> Unit,
                pizzaViewModel: PizzaViewModel,
) {
    pizzaViewModel.getOrders()
    val orders = pizzaViewModel.orders.toMutableList()
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Admin Page",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 16.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackHomeClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                IconButton(
                    onClick = {
                        pizzaViewModel.currentUser = null
                        pizzaViewModel.currentUserRole = -1
                        onLogoutButtonClick()
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "Logout"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn() {
                    items(orders.filter { it.Status == 0 }.size) { index ->
                        val order = orders.filter { it.Status == 0 }[index]

                        Column {
                            Text(text = "Order ID: ${order.BestellingId}")
                            Text(text = "User ID: ${order.UserId}")
                            Text(text = "Comment: ${order.Comment}")
                            Text(text = "Pickup Time: ${order.PickupTime}")
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Status: ${order.Status}", modifier = Modifier.weight(1f))
                                Button(
                                    onClick = { pizzaViewModel.updateOrder(order.BestellingId) },
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(text = "DONE")
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
