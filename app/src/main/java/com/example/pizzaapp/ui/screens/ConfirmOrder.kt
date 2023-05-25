package com.example.pizzaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConfirmOrder(
    modifier: Modifier = Modifier,
    onBackHomeClick: () -> Unit,
    pizzaViewModel: PizzaViewModel
) {
    val currentUser = pizzaViewModel.currentUser

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Confirmation",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackHomeClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
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
                    text = "Thank you for your order, ${pizzaViewModel.currentUser!!.FN}!",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Order details:",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                // Display order details here, such as items, total amount, etc.
            }
        }
    )
}
