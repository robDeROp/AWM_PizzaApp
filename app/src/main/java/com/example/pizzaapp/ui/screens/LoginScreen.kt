package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzaapp.model.CurrentUser
import com.example.pizzaapp.ui.theme.Primary_200
import com.example.pizzaapp.ui.theme.Primary_700

//onRegisterClick



@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onBackHomeClick: () -> Unit,
    onRegisterClick: () -> Unit,
    ToAccount: ()-> Unit,
    pizzaViewModel: PizzaViewModel
) {


    var userID by remember { mutableStateOf(0) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoginEnabled = email.isNotBlank() && password.isNotBlank()

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
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 16.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackHomeClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Primary_200
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
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
                    label = { Text("Wachtwoord") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        pizzaViewModel.loginUser(email, password)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Primary_700), // Set your desired color here

                            enabled = isLoginEnabled
                ) {
                    Text(text = "Login")
                }
                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        onRegisterClick() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Primary_700) // Set your desired color here
                ) {
                    Text(text = "Nog geen account?")
                }
            }
        }
    }
}