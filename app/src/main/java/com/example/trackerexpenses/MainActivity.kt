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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material.ScaffoldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.trackerexpenses.bottomNavigation.BottomNavbar
import com.example.trackerexpenses.navigation.RootNavGraph
import com.example.trackerexpenses.navigation.RouteApp
import com.example.trackerexpenses.ui.theme.TrackerExpensesTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
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
    val fabVisibleState = remember { mutableStateOf(false) }
    val bottomNavVisibleState = remember { mutableStateOf(false) }

    Scaffold(containerColor = Color.Black, modifier = Modifier.fillMaxSize().navigationBarsPadding(), bottomBar = {
        BottomNavbar(
            navController = navController,
            onBackPressedDispatcher = onBackPressedDispatcher, isVisible = {  bottomNavVisibleState.value = it},  fabVisibleState = fabVisibleState
        )

    }, floatingActionButtonPosition = FabPosition.Center,   floatingActionButton = {
        if (fabVisibleState.value) {
            FloatingActionButton(
                shape = CircleShape,
                modifier = Modifier,
                onClick = {
                    RouteApp.AddScreen.route.let {
                        navController.navigate(it) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    RouteApp.AddScreen.route.let { navController.navigate(it) }

                },
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add icon")
            }
        }
    }, content = { padding ->
        ScaffoldDefaults.contentWindowInsets
        Box(modifier = Modifier.padding(padding)) {
            RootNavGraph(navController = navController)
        }
    })
}