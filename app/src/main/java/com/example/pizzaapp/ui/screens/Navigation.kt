package com.example.pizzaapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marsphotos.ui.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val PizzaUiState =
                HomeScreen(PizzaUiState) }
        composable("error") { ErrorScreen() }
        composable("loading") { LoadingScreen() }
        composable("admin") { AdminScreen() }
        composable("register") { RegisterScreen() }
        composable("shoppingCart") { ShoppingCartScreen() }

    }
}