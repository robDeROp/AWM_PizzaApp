package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
    val availableStatus = listOf("Allemaal", "Geannuleerd", "Bezig", "Klaar")
    val selectedStatus = remember { mutableStateOf("Bezig") }

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
                    text = "Admin scherm",
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
        val filteredOrders = when (selectedStatus.value) {
            "Allemaal" -> orders
            "Geannuleerd" -> orders.filter { it.Status == -1 }
            "Bezig" -> orders.filter { it.Status == 0 }
            "Klaar" -> orders.filter { it.Status == 1 }
            else -> orders
        }
        var expanded by remember { mutableStateOf(false) }
        Text(
            text = "Bestelling Geschiedenis",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
            modifier = Modifier.padding(vertical = 8.dp).align(Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .clickable(onClick = { expanded = true })
                    .background(MaterialTheme.colors.surface)
                    .border(
                        1.dp,
                        MaterialTheme.colors.onSurface,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedStatus.value,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(MaterialTheme.colors.surface)
                    .border(
                        1.dp,
                        MaterialTheme.colors.onSurface,
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .heightIn(max = 240.dp)
                        .padding(vertical = 4.dp)
                ) {
                    availableStatus.forEach { status ->
                        DropdownMenuItem(
                            onClick = {
                                selectedStatus.value = status
                                expanded = false
                            }
                        ) {
                            Text(
                                text = status,
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = 12.dp
                                )
                            )
                        }
                    }
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn() {
                    items(filteredOrders.size) { index ->
                        val order = filteredOrders[index]

                        Text(text = "Bestelling ID: ${order.BestellingId}")
                        Text(text = "Gebruiker ID: ${order.UserId}")
                        Text(text = "Opmerking: ${order.Comment}")
                        Text(text = "Ophaal moment: ${order.PickupTime}")
                        Text(text = "Status: ${getStatusText(order.Status)}")
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (order.Status != 1) {
                                Button(
                                    onClick = { pizzaViewModel.updateOrder(order.BestellingId, 1) },
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                                ) {
                                    Text(text = "Klaar")
                                }
                            }
                            if (order.Status != -1) {
                                Button(
                                    onClick = { pizzaViewModel.updateOrder(order.BestellingId, -1) },
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                                ) {
                                    Text(text = "Annuleer")
                                }
                            }
                            Button(
                                onClick = {
                                    pizzaViewModel.getLines(order.BestellingId)
                                    ToDetails()
                                },
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(text = "Details")
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
@Composable
private fun getStatusText(status: Int): String {
    return when (status) {
        -1 -> "Geannuleerd"
        0 -> "Bezig"
        1 -> "Klaar"
        else -> "Onbekend"
    }
}