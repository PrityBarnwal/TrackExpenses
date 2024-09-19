package com.example.trackerexpenses.screen.authScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.trackerexpenses.navigation.RouteApp


@Composable
fun CreateAccountScreen(navController: NavController) {
    Button(onClick = { navController.navigate(RouteApp.HomeScreen.route) }) {
        Text(text = "CreateAccount", color = Color.Red)
    }
}