package com.example.trackerexpenses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.trackerexpenses.bottomNavigation.BottomNavbar
import com.example.trackerexpenses.navigation.RootNavGraph
import com.example.trackerexpenses.ui.theme.TrackerExpensesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*val viewModel: GroceryViewModel by viewModels()
            TrackerExpensesTheme {
                GroceryScreen(viewModel)
            }*/

            val onBackPressedDispatcher = this.onBackPressedDispatcher
            TrackerExpensesTheme {
                SetupNavigation(onBackPressedDispatcher)
            }
        }
    }
}

@Composable
private fun SetupNavigation(
    onBackPressedDispatcher: OnBackPressedDispatcher
) {

    val navController = rememberNavController()
    Scaffold(containerColor = Color.White, modifier = Modifier.fillMaxSize().navigationBarsPadding(), bottomBar = {
        BottomNavbar(
            navController = navController,
            onBackPressedDispatcher = onBackPressedDispatcher,
        ) {}

    }, content = { padding ->
        ScaffoldDefaults.contentWindowInsets
        Box(modifier = Modifier.padding(padding)) {
            RootNavGraph(navController = navController)
        }
    })
}