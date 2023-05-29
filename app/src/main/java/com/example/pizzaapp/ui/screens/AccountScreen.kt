package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzaapp.model.OrderHistoryLine

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    onBackHomeClick: () -> Unit,
    pizzaViewModel: PizzaViewModel
) {
    val currentUser = pizzaViewModel.currentUser
    val userOrders = pizzaViewModel.userOrders
    val filteredUserOrders = remember { mutableStateOf(userOrders) }
    val availableStatus = listOf("All", "Cancelled", "Busy", "Done")
    val selectedStatus = remember { mutableStateOf("All") }

    // Fetch user order history
    LaunchedEffect(currentUser) {
        currentUser?.let {
            pizzaViewModel.getUserOrderHistory(it.id)
        }
    }

    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Text(
                    text = "Account Pagina",
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

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Account Details",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                modifier = Modifier.padding(vertical = 8.dp).align(Alignment.CenterHorizontally)
            )

            if (currentUser != null) {
                UserInfoRow("User ID", currentUser.id.toString())
                UserInfoRow("First Name", currentUser.FN)
                UserInfoRow("Last Name", currentUser.LN)
                UserInfoRow("Email", currentUser.E)
                UserInfoRow("Phone Number", currentUser.PN)
                UserInfoRow("Role", currentUser.R.toString())
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
        // Filter user order history by status
        val filteredOrders = when (selectedStatus.value) {
            "All" -> userOrders
            "Cancelled" -> userOrders.filter { it.Status == -1 }
            "Busy" -> userOrders.filter { it.Status == 0 }
            "Done" -> userOrders.filter { it.Status == 1 }
            else -> userOrders
        }
        filteredUserOrders.value = filteredOrders

        // Display filter dropdown
        var expanded by remember { mutableStateOf(false) }
        Text(
            text = "Bestelling Geschiedenis",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
            modifier = Modifier.padding(vertical = 8.dp).align(Alignment.CenterHorizontally)
        )
        Box(modifier = Modifier
            .fillMaxWidth() // Set the width to 80% of the available space
            .padding(horizontal = 16.dp) // Add horizontal padding
        )  {

            Box(
                modifier = Modifier
                    .fillMaxWidth() // Set the width to 80% of the available space
                    .height(24.dp) // Increase the height
                    .clickable(onClick = { expanded = true }) // Make it clickable
                    .background(MaterialTheme.colors.surface)
                    .border(1.dp, MaterialTheme.colors.onSurface, shape = MaterialTheme.shapes.small)
                    .padding(horizontal = 16.dp) // Add horizontal padding
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedStatus.value,
                        modifier = Modifier.weight(1f) // Allow the text to expand horizontally
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
                    .border(1.dp, MaterialTheme.colors.onSurface, shape = MaterialTheme.shapes.small)
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(IntrinsicSize.Max) // Adjust width to match content
                        .heightIn(max = 240.dp) // Set a maximum height for scrolling
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
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp) // Adjust padding
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
        // Display filtered user order history
        LazyColumn(modifier =
        Modifier.padding(horizontal = 16.dp).weight(1f)) {
            val invertedFilteredUserOrders =  mutableStateOf(filteredUserOrders.value.reversed())
            items(invertedFilteredUserOrders.value) { order ->
                OrderHistoryItem(order)
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
        }

        Button(
            onClick = {
                pizzaViewModel.currentUser = null
                pizzaViewModel.currentUserRole = -1
                onBackHomeClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Log Out")
        }
    }
}
@Composable
fun OrderHistoryItem(order: OrderHistoryLine) {
    val statusText = when (order.Status) {
        -1 -> "Cancelled"
        0 -> "Busy"
        1 -> "Done"
        else -> "Unknown"
    }

    UserInfoRow("Bestelling ID", order.BestellingId.toString())
    UserInfoRow("Pickup Time", order.PickupTime)
    UserInfoRow("Status", statusText)
}

@Composable
fun UserInfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = TextStyle(fontWeight = FontWeight.Light, fontSize = 14.sp),
            modifier = Modifier.weight(1f)
        )
    }
}