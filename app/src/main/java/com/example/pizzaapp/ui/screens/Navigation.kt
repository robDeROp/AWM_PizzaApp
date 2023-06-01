package com.example.pizzaapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pizzaapp.model.CurrentUser
import com.example.pizzaapp.model.ShoppingCartLine
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val pizzaViewModel: PizzaViewModel = viewModel()


    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val shoppingCartList = remember { mutableStateListOf<ShoppingCartLine>() }
            val pizzas = pizzaViewModel.pizzas
            MenuList(
                pizzas = pizzas,
                onCartButtonClick = {
                    navController.navigate("shoppingCart")
                },
                onAccountButtonClick = {
                    if (pizzaViewModel.currentUserRole == 0) {
                        navController.navigate("account")
                    } else if (pizzaViewModel.currentUserRole == 1) {
                        navController.navigate("admin")
                    } else {
                        navController.navigate("login")
                    }
                },
                pizzaViewModel = pizzaViewModel
            )
        }
        composable("error") { ErrorScreen() }
        composable("details") {
            OrderDetails(
                onBackHomeClick = {
                    navController.navigate("admin")
                },
                pizzaViewModel = pizzaViewModel
            )
        }
        composable("login") {
            LoginScreen(
                onBackHomeClick = {
                    navController.navigate("home")
                },
                onRegisterClick = {
                    navController.navigate("register")
                },
                ToAccount = {
                    if (pizzaViewModel.currentUserRole == 0) {
                        navController.navigate("account")
                    } else if (pizzaViewModel.currentUserRole == 1) {
                        navController.navigate("admin")
                    } else {
                        navController.navigate("account")
                    }
                },
                pizzaViewModel = pizzaViewModel
            )
        }
        composable("register") {
            RegisterScreen(
                onBackHomeClick = {
                    navController.navigate("home")
                },
                onLoginClick = {
                    navController.navigate("login")
                },
                ToAccount = {
                    if (pizzaViewModel.currentUserRole == 0) {
                        navController.navigate("account")
                    } else if (pizzaViewModel.currentUserRole == 1) {
                        navController.navigate("admin")
                    } else {
                        navController.navigate("account")
                    }
                },
                pizzaViewModel = pizzaViewModel
            )
        }
        composable("admin") {
            AdminScreen(
                pizzaViewModel = pizzaViewModel,
                onAccountButtonClick = { navController.navigate("account") },
                ToDetails = { navController.navigate("details") },
                onLogoutButtonClick = {
                    navController.navigate("login")
                    pizzaViewModel.currentUserRole = 0
                }
            )
        }
        composable("confirmOrder") {
            ConfirmOrder(
                pizzaViewModel = pizzaViewModel,
                onBackHomeClick = { navController.navigate("home") },
            )
        }
        composable("account") {
            AccountScreen(
                pizzaViewModel = pizzaViewModel,
                onBackHomeClick = {
                    if (pizzaViewModel.currentUserRole == 0) {
                        navController.navigate("home")
                    } else if (pizzaViewModel.currentUserRole == 1) {
                        navController.navigate("admin")
                    } else {
                        navController.navigate("login")
                    }
                },
            )
        }
        composable("order") {
            OrderScreen(
                onBackHomeClick = { navController.navigate("home") },
                onOrderConfirm = { navController.navigate("confirmOrder") },
                pizzaViewModel = pizzaViewModel

            )
        }
        composable("shoppingCart") {
            ShoppingCartScreen(
                onBackHomeClick = { navController.navigate("home") },
                onPlaceOrder = { navController.navigate("order") },
                toLogin = { navController.navigate("login") },
                pizzaViewModel = pizzaViewModel
            )
        }
    }
}