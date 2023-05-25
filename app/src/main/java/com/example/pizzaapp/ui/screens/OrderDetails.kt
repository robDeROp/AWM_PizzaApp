package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrderDetails(modifier: Modifier = Modifier,
                  onBackHomeClick: () -> Unit,
                  pizzaViewModel: PizzaViewModel
                  ) {

    val currentOrder = pizzaViewModel.orderDetails

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Order Details",
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

        LazyColumn() {
            items(currentOrder.size) { index ->
                val order = currentOrder[index]

                Column {
                    Text(text = "Pizza ID: ${order.PizzaId}")
                    Text(text = "Quantity: ${order.Quantity}")
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