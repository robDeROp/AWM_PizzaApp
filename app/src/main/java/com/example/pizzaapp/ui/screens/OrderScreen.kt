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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    onBackHomeClick: () -> Unit,
    onOrderConfirm: () -> Unit,
    pizzaViewModel: PizzaViewModel
) {
    var shoppingCartLines = pizzaViewModel.shoppingCartList

    val hours = remember { mutableStateOf("") }
    val minutes = remember { mutableStateOf("") }
    val comment = remember { mutableStateOf("") }
    val isTimeValid = remember { mutableStateOf(true) } // Track if the selected time is valid

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
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val currentTime = LocalTime.now()
                val currentHours = currentTime.hour.toString()
                val currentMinutes = currentTime.minute.toString()

                TextField(
                    value = hours.value.takeIf { it.isNotEmpty() } ?: currentHours,
                    onValueChange = { value ->
                        hours.value = value
                    },
                    label = { Text("Hours") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                TextField(
                    value = minutes.value.takeIf { it.isNotEmpty() } ?: currentMinutes,
                    onValueChange = { value ->
                        minutes.value = value
                    },
                    label = { Text("Minutes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
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
                        val selectedHours = hours.value.toIntOrNull() ?: 0
                        val selectedMinutes = minutes.value.toIntOrNull() ?: 0
                        val isTimeValid = isValidTime(selectedHours, selectedMinutes, currentTime)
                        if (isTimeValid) {
                            pizzaViewModel.PlaceYourOrder(selectedHours, selectedMinutes, comment.value)
                        } else {
                            // Set time validity to false if it's not valid
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Place Order")
                }
                if (!isTimeValid.value) {
                    Text(
                        text = "Time Not Valid",
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isValidTime(selectedHours: Int, selectedMinutes: Int, currentTime: LocalTime): Boolean {
    val currentHours = currentTime.hour
    val currentMinutes = currentTime.minute

    if (selectedHours > currentHours) {
        return true
    } else if (selectedHours == currentHours && selectedMinutes > currentMinutes) {
        return true
    }
    return false
}
