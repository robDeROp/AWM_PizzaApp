package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrderScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Plaats je order",
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}