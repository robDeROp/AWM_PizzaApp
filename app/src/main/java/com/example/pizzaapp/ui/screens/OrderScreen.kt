package com.example.pizzaapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzaapp.ui.theme.Primary_200
import com.example.pizzaapp.ui.theme.Primary_700
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

    val currentTime = LocalTime.now()
    val currentHours = currentTime.hour.toString()
    val currentMinutes = currentTime.minute.toString()

    val hours = remember { mutableStateOf(currentHours) }
    val minutes = remember { mutableStateOf(currentMinutes) }
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
                    text = "Plaats je bestelling",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 16.dp),
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackHomeClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Primary_200

        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                val currentTime = LocalTime.now()
                val currentHours = currentTime.hour.toString()
                val currentMinutes = currentTime.minute.toString()

                Text(
                    text = "Plaats je bestelling",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Vul het tijdstip in waarop je de bestelling wilt ophalen. De bestelling moet op dezelfde dag worden opgehaald.",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = hours.value.takeIf { it.isNotEmpty() } ?: currentHours,
                        onValueChange = { value ->
                            hours.value = value
                        },
                        label = { Text("Uur") },
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(end = 8.dp)
                    )
                    TextField(
                        value = minutes.value.takeIf { it.isNotEmpty() } ?: currentMinutes,
                        onValueChange = { value ->
                            minutes.value = value
                        },
                        label = { Text("Minuten") },
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(start = 8.dp)
                    )
                }

                TextField(
                    value = comment.value,
                    onValueChange = { value -> comment.value = value },
                    label = { Text("Opmerking") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                var isTimeValid by remember { mutableStateOf(true) } // Initialize as true

                Button(
                    onClick = {
                        val selectedHours = hours.value.toIntOrNull() ?: 0
                        val selectedMinutes = minutes.value.toIntOrNull() ?: 0
                        isTimeValid = isValidTime(selectedHours, selectedMinutes, currentTime)

                        if (isTimeValid) {
                            pizzaViewModel.PlaceYourOrder(
                                selectedHours,
                                selectedMinutes,
                                comment.value
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Primary_700) // Set your desired color here

                ) {
                    Text(text = "Bestelling plaatsen")
                }

                if (!isTimeValid) {
                    Text(
                        text = "Tijdstip is niet geldig!",
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Text(
                    text = "Je kunt de status van je bestelling volgen op je accountpagina.",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isValidTime(selectedHours: Int, selectedMinutes: Int, currentTime: LocalTime): Boolean {
    val currentHours = currentTime.hour
    val currentMinutes = currentTime.minute

    if (selectedHours in 0..23 && selectedMinutes in 0..59) {
        if (selectedHours > currentHours) {
            return true
        } else if (selectedHours == currentHours && selectedMinutes > currentMinutes) {
            return true
        }
    }
    return false
}
