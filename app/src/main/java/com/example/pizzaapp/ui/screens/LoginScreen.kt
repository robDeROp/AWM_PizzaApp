package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val pizzaViewModel: PizzaViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoginEnabled = email.isNotBlank() && password.isNotBlank()

    when (val uiState = pizzaViewModel.pizzaUiState) {
        is PizzaUiState.LoginSuccess -> {
            // Login successful, display user information
            val user = uiState.user
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Welcome, ${user.FN} ${user.LN}!",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text("Email: ${user.E}")
                    Text("Phone Number: ${user.PN}")

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { pizzaViewModel.LogOut() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Logout")
                    }
                }
            }
        }
        PizzaUiState.Error -> {
            // Display error label on the login screen
            Text("Error occurred while logging in.", color = Color.Red)
        }
        PizzaUiState.Loading -> {
            // You can replace the following with your Loading screen implementation
            Text("Loading...")
        }
        else -> {
            // Login form
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { pizzaViewModel.tryLogin(email, password) },
                        enabled = isLoginEnabled,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Login")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { pizzaViewModel.ToRegister() },
                        enabled = isLoginEnabled,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Al een Account?")
                    }
                }
            }
        }

    }
}