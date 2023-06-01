package com.example.pizzaapp.ui.screens

import android.annotation.SuppressLint
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
import com.example.pizzaapp.ui.theme.Primary_200

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConfirmOrder(
    modifier: Modifier = Modifier,
    onBackHomeClick: () -> Unit,
    pizzaViewModel: PizzaViewModel
) {
    val currentUser = pizzaViewModel.currentUser
    var shoppingCartLines = pizzaViewModel.shoppingCartList
    val cartLinesState = remember { mutableStateListOf(*shoppingCartLines.toTypedArray()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bevestiging",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackHomeClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Primary_200

            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bedankt voor je bestelling, ${pizzaViewModel.currentUser!!.FN}!",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Order details:",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                // Display order details here, such as items, total amount, etc.
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {

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
                                    text = "Aantal: ${line.quantity}",
                                    modifier = Modifier.width(80.dp),
                                    fontSize = 16.sp // Increased text size
                                )
                                Text(
                                    text = "Totaal: ${
                                        String.format(
                                            "%.2f",
                                            line.quantity * line.pizza.price
                                        )
                                    }€",
                                    modifier = Modifier.width(100.dp),
                                    fontSize = 16.sp // Increased text size
                                )
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
    )
}
