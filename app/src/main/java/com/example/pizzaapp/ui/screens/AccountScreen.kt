package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AccountScreen(modifier: Modifier = Modifier,
                  onBackHomeClick: () -> Unit,
                  pizzaViewModel: PizzaViewModel
                  ) {

    val currentUser = pizzaViewModel.currentUser

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Account Page",
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

        if (currentUser != null) {
            UserInfoRow("User ID", currentUser.id.toString())
            UserInfoRow("First Name", currentUser.FN)
            UserInfoRow("Last Name", currentUser.LN)
            UserInfoRow("Email", currentUser.E)
            UserInfoRow("Phone Number", currentUser.PN)
            UserInfoRow("Role", currentUser.R.toString())
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
fun UserInfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )
    }
}