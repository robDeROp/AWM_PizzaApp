package com.example.pizzaapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    onBackHomeClick: () -> Unit,
    onOrderConfirm: () -> Unit,
    pizzaViewModel: PizzaViewModel
) {
    val hours = remember { mutableStateOf("") }
    val minutes = remember { mutableStateOf("") }
    val comment = remember { mutableStateOf("") }
    when (pizzaViewModel.orderUIState) {
        is OrderUIState.Success -> {
            pizzaViewModel.ResetUIOrderState()
            onOrderConfirm()
        }
        is OrderUIState.Empty -> {}
        else -> {}
    }
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Place Order",
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
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = hours.value,
                        onValueChange = { value -> hours.value = value },
                        label = { Text("Hours") },
                        modifier = Modifier.padding(horizontal = 1.dp)
                    )
                    Text(
                        text = ":",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(horizontal = 1.dp)
                    )
                    TextField(
                        value = minutes.value,
                        onValueChange = { value -> minutes.value = value },
                        label = { Text("Minutes") },
                        modifier = Modifier.padding(horizontal = 1.dp)
                    )
                }
                TextField(
                    value = comment.value,
                    onValueChange = { value -> comment.value = value },
                    label = { Text("Comment") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Button(
                    onClick = {
                        val hoursValue = hours.value.toIntOrNull() ?: 0
                        val minutesValue = minutes.value.toIntOrNull() ?: 0
                        pizzaViewModel.PlaceYourOrder(hoursValue, minutesValue, comment.value)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Place Order")
                }
            }
        }
    }
}