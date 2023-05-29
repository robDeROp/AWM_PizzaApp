package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pizzaapp.Network.getDetailsResponse

@Composable
fun OrderDetails(
    modifier: Modifier = Modifier,
    onBackHomeClick: () -> Unit,
    pizzaViewModel: PizzaViewModel
) {
    var currentOrder: List<getDetailsResponse> by remember { mutableStateOf(emptyList()) }
    when (val uiState = pizzaViewModel.orderDetailsUIState) {
        is OrderDetaiUIState.Loading -> LoadingScreen(modifier)
        is OrderDetaiUIState.Success -> {
            pizzaViewModel.ResetUIOrderDetailsState()
            currentOrder = pizzaViewModel.orderDetails
        }
        is OrderDetaiUIState.Error -> ErrorScreen(modifier)
        is OrderDetaiUIState.Empty -> { /* Nothing to show */ }
        else -> {}
    }

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

        if(currentOrder!=null) {
            LazyColumn {
                items(currentOrder.size) { index ->
                    val order = currentOrder[index]

                    Column {
                        Text(text = "Pizza ID: ${order.PizzaName}")
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
}
