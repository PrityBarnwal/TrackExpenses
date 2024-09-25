package com.example.trackerexpenses.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trackerexpenses.GroceryViewModel


@Composable
fun TransactionScreen(navController: NavController) {
    val viewModel = viewModel<GroceryViewModel>()
    val groceryItems by viewModel.groceryItems

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Transaction",
            style = MaterialTheme.typography.subtitle1,
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
        )
        LazyColumn {
            items(groceryItems) { item ->
                CardHomeRecentTransaction(
                    category = item.name,
                    description = item.note,
                    price = "-${item.price}",
                    time = "$currentTime"
                )
            }
        }
    }
}