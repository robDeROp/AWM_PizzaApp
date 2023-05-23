
package com.example.pizzaapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzaapp.R
import com.example.pizzaapp.ui.screens.AppNavigation
import com.example.pizzaapp.ui.screens.PizzaViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PizzaBestelApp(modifier: Modifier = Modifier) {
    val pizzaViewModel: PizzaViewModel = viewModel()
    pizzaViewModel.getPizzas()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(
                        onClick = {
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            // Handle shopping action
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Login"
                            )
                        }
                    )
                }
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                AppNavigation()
            }
        }
    )
}