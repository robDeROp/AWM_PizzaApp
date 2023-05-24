package com.example.pizzaapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pizzaapp.model.CurrentUser
import com.example.pizzaapp.model.ShoppingCartLine
var _currentUser: CurrentUser? = null
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val pizzaViewModel: PizzaViewModel = viewModel()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val viewModel: PizzaViewModel = viewModel()
            val shoppingCartList = remember { mutableStateListOf<ShoppingCartLine>() }
            val pizzas = viewModel.pizzas

            MenuList(pizzas = pizzas, onCartButtonClick = { item ->
                shoppingCartList += item
                navController.navigate("shoppingCart")
            }, onAccountButtonClick = {
                navController.navigate("login")
            })
        }
        composable("error") { ErrorScreen() }
        composable("login") {
            if (_currentUser == null) {
                LoginScreen(
                    onBackHomeClick = {
                        navController.navigate("home")
                    },
                    onRegisterClick = {
                        navController.navigate("register")
                    },
                    ToAccount = { receivedCurrentUser ->
                        // Handle the receivedCurrentUser here
                        // You can store it in a variable or perform any other actions
                        // For example, updating _currentUser variable:
                        _currentUser = receivedCurrentUser
                    }
                )
            }
            else{
                AccountScreen(
                    currentUser = _currentUser,
                    onBackHomeClick = {
                        navController.navigate("home")
                    })
            }
        }
        composable("register") {
            RegisterScreen(onBackHomeClick = {
                navController.navigate("home")
            },
                onLoginClick = {
                    navController.navigate("login")
                })
        }
        composable("admin") { AdminScreen() }
        composable("shoppingCart") {
            ShoppingCartScreen(shoppingCartList, onBackHomeClick = {
                navController.navigate("home")})
        }
    }
}