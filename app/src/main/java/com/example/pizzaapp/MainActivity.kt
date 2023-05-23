package com.example.pizzaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pizzaapp.ui.theme.MarsPhotosTheme
import com.example.pizzaapp.ui.PizzaBestelApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarsPhotosTheme {
                PizzaBestelApp()
            }
        }
    }
}