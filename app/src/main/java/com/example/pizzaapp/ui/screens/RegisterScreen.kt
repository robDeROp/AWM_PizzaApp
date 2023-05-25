package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterScreen(modifier: Modifier = Modifier,
                   onBackHomeClick: () -> Unit,
                   onLoginClick: () -> Unit,
                   ToAccount: ()-> Unit,
                   pizzaViewModel: PizzaViewModel
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNr by remember { mutableStateOf("") }

    when (pizzaViewModel.loginUIState) {
        is LoginUIState.Loading -> LoadingScreen(modifier)
        is LoginUIState.Success -> {
            pizzaViewModel.ResetUIState()
            ToAccount()
        }
        is LoginUIState.Error -> ErrorScreen(modifier)
        is LoginUIState.Empty -> { /* Nothing to show */ }
        else -> {}
    }

    val isRegisterEnabled = firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            phoneNr.isNotBlank()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Registreer",
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
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
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

                OutlinedTextField(
                    value = phoneNr,
                    onValueChange = { phoneNr = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { pizzaViewModel.registerUser(firstName, lastName, email, password, phoneNr) },
                    enabled = isRegisterEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Register")
                }
                Button(
                    onClick = { onLoginClick() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Al een account?")
                }
            }
        }
    }
}