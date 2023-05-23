package com.example.pizzaapp.ui.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pizzaapp.model.Pizza
import com.example.pizzaapp.model.ShoppingCartLine

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val pizzaViewModel: PizzaViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val shoppingCartList = mutableListOf<ShoppingCartLine>()
            MenuList(pizzas = listOf(
                Pizza(1, "Pizza Margherita", 12.3, "", "Fresh tomato sauce, mozzarella, and basil"),
            ), OnCartButtonClick = { item ->
                shoppingCartList += item
                navController.navigate("shoppingCart")
            })
        }
        composable("error") { ErrorScreen() }
        composable("loading") { LoadingScreen() }
        composable("admin") { AdminScreen() }
        composable("shoppingCart") {
            ShoppingCartScreen(shoppingCartList)
        }
    }
}