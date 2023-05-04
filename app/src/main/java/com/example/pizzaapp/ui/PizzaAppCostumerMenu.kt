package com.example.pizzaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pizzaapp.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
private fun PizzaCostumerMenuScreen(
    pizzas: Array<PizzaMenuItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.Pizza_app),
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = stringResource(R.string.Pizza_app_menu),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(pizzas.size) { index ->
                    MenuItem(
                        name = pizzas[index].name,
                        prijs = pizzas[index].prijs,
                        beschrijving = pizzas[index].beschrijving
                    )
                    Divider(modifier = Modifier.fillMaxWidth().height(1.dp).padding(top = 0.dp), color = androidx.compose.ui.graphics.Color.Gray)

                }
            }
        }
    }
}


@Composable
private fun MenuItem(
    name: String,
    prijs: Double,
    beschrijving: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .padding(16.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = beschrijving,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.add_to_cart),
                    contentDescription = null,
                    modifier = Modifier.height(20.dp),
                )
                Text(
                    text = "$prijs €",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun PizzaAppCostumerMenu() {
    val menuItems = remember {
        arrayOf(
            PizzaMenuItem("Margherita", 8.99, "Tomato sauce, mozzarella, and basil"),
            PizzaMenuItem("Pepperoni", 10.99, "Tomato sauce, mozzarella, and pepperoni"),
            PizzaMenuItem("Vegetarian", 9.99, "Tomato sauce, mozzarella, mushrooms, and bell peppers"),
            PizzaMenuItem("Hawaiian", 12.99, "Tomato sauce, mozzarella, ham, and pineapple"),
            PizzaMenuItem("Meat Lovers", 13.99, "Tomato sauce, mozzarella, pepperoni, sausage, bacon, and ham"),
            PizzaMenuItem("BBQ Chicken", 11.99, "BBQ sauce, mozzarella, grilled chicken, and red onions"),
            PizzaMenuItem("Mushroom and Olive", 10.99, "Tomato sauce, mozzarella, mushrooms, and olives"),
            PizzaMenuItem("Four Cheese", 12.99, "Tomato sauce, mozzarella, parmesan, feta, and gorgonzola"),
            PizzaMenuItem("Supreme", 14.99, "Tomato sauce, mozzarella, pepperoni, sausage, mushrooms, onions, and bell peppers")
        )
    }

    PizzaCostumerMenuScreen(pizzas = menuItems)
}

@Preview
@Composable
fun PizzaAppCostumerMenuPreview() {
    MaterialTheme {
        PizzaAppCostumerMenu()
    }
}
