package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminScreen(modifier: Modifier = Modifier) {
    Text(
        text = "AdminScreen",
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(vertical = 16.dp)
    )

    Text(
        text = "Order Overzicht",
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}