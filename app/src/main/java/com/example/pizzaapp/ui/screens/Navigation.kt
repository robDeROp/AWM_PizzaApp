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
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val pizzaViewModel: PizzaViewModel = viewModel()


    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val shoppingCartList = remember { mutableStateListOf<ShoppingCartLine>() }
            val pizzas = pizzaViewModel.pizzas

            MenuList(pizzas = pizzas, onCartButtonClick = { item ->
                shoppingCartList += item
                navController.navigate("shoppingCart")
            }, onAccountButtonClick = {
                navController.navigate("login")
            },
                pizzaViewModel = pizzaViewModel
            )
        }
        composable("error") { ErrorScreen() }
        composable("login") {
            if (pizzaViewModel.currentUserRole == -1) {
                LoginScreen(
                    onBackHomeClick = {
                        navController.navigate("home")
                    },
                    onRegisterClick = {
                        navController.navigate("register")
                    },
                    ToAccount = {
                        if(pizzaViewModel.currentUserRole==0){
                            navController.navigate("shoppingCart")
                        }
                        else if(pizzaViewModel.currentUserRole==1){
                            navController.navigate("admin")
                        }
                        else{
                            navController.navigate("home")
                        }
                    },
                    pizzaViewModel = pizzaViewModel
                )
            }
            else{
                if(pizzaViewModel.currentUserRole==0){
                    navController.navigate("home")
                }
                else if(pizzaViewModel.currentUserRole==1){
                    navController.navigate("admin")
                }
                else{
                    navController.navigate("error")
                }
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
        composable("order") { OrderScreen() }
        composable("shoppingCart") {
            ShoppingCartScreen(shoppingCartList,
                onBackHomeClick = { navController.navigate("home")},
                onPlaceOrder = { navController.navigate("order")},
                toLogin = { navController.navigate("login")},
                pizzaViewModel = pizzaViewModel
            )
        }
    }
}