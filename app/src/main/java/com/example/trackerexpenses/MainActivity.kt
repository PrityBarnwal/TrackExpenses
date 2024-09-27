package com.example.trackerexpenses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.trackerexpenses.bottomNavigation.BottomNavbar
import com.example.trackerexpenses.navigation.RootNavGraph
import com.example.trackerexpenses.navigation.RouteApp
import com.example.trackerexpenses.ui.theme.TrackerExpensesTheme
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

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
    var expanded by remember { mutableStateOf(false) }


    Scaffold(
        backgroundColor = Color.Black,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        bottomBar = {
            if (bottomNavVisibleState.value) {
                BottomAppBar(
                    cutoutShape = CircleShape,
                    backgroundColor = Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    content = {
                        BottomNavbar(
                            navController = navController,
                            onBackPressedDispatcher = onBackPressedDispatcher,
                            fabVisibleState = fabVisibleState
                        )
                    }
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (fabVisibleState.value) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        expanded = !expanded
                    },
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.Close else Icons.Filled.Add,
                        contentDescription = "Add icon"
                    )
                }
            }
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                RootNavGraph(navController = navController)
            }
        }
    )

    CircularButtons(expanded, onAddTransaction = {
        expanded = !expanded
        RouteApp.AddTransactionScreen.route.let {
            navController.navigate(it) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }, onAddIncome = {
        expanded = !expanded
        RouteApp.AddIncomeScreen.route.let {
            navController.navigate(it) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    })
    // Call this effect to update bottom bar visibility whenever the navigation state changes
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Update the visibility of the bottom navigation based on the destination route
            bottomNavVisibleState.value = when (destination.route) {
                RouteApp.HomeScreen.route,
                RouteApp.TransactionScreen.route,
                RouteApp.AddTransactionScreen.route,
                RouteApp.AddIncomeScreen.route,
                RouteApp.ReceiptScreen.route,
                RouteApp.ProfileScreen.route -> true

                else -> false
            }
        }
    }
}

@Composable
fun CircularButtons(expanded: Boolean, onAddTransaction: () -> Unit, onAddIncome: () -> Unit) {
    AnimatedVisibility(visible = expanded) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
        ) {
            val numberOfButtons = 3
            val angles = listOf(0f, 90f, 180f) // Angles for buttons 0, 1, and 2
            val radii = listOf(150.dp, 110.dp, 180.dp) // Radii for buttons 0-1, 1-2, and 0-2

            for (i in 0 until numberOfButtons) {
                // Calculate angle in radians based on the specified angles
                val angleInRadians =
                    Math.toRadians(angles[i].toDouble() - 180) // Start from -90 degrees for proper alignment

                // Use different radii for each button
                val radius = radii[i]

                // Calculate x and y based on radius and angle
                val x = radius.value * cos(angleInRadians).toFloat()
                val y = radius.value * sin(angleInRadians).toFloat()

                FloatingActionButton(
                    onClick = {
                        when (i) {
                            0 -> {
                                onAddTransaction.invoke()
                                println("Transaction button clicked")
                            }

                            1 -> {
                                onAddIncome.invoke()
                                println("Income button clicked")
                            }

                            2 -> {
                                println("Warning button clicked")
                            }
                        }
                    },
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = x
                            translationY = y
                        }
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = when (i) {
                            0 -> Icons.Default.Share
                            1 -> Icons.Default.Build
                            else -> Icons.Default.Warning
                        },
                        contentDescription = "Button ${i + 1}", tint = Color.White
                    )
                }
            }
        }
    }
}

