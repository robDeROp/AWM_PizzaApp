package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pizzaapp.model.CurrentUser

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    currentUser: CurrentUser? = null,
    onBackHomeClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Account",
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
    if (currentUser!=null){
        Column(modifier = modifier.padding(16.dp)) {
            Text(text = "User ID: ${currentUser.id}")
            Text(text = "First Name: ${currentUser.FN}")
            Text(text = "Last Name: ${currentUser.LN}")
            Text(text = "Email: ${currentUser.E}")
            Text(text = "Phone Number: ${currentUser.PN}")
            Text(text = "Role: ${currentUser.R}")
        }
    }
}