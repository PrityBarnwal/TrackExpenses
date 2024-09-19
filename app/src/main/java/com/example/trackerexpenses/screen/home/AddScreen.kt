package com.example.trackerexpenses.screen.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.trackerexpenses.navigation.RouteApp


@Composable
fun AddScreen(navController: NavController) {
    Button(onClick = { navController.navigate(RouteApp.HomeScreen.route) }) {
        Text(text = "AddScreen", color = Color.Red)
    }
}