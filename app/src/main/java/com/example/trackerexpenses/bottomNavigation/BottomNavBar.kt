package com.example.trackerexpenses.bottomNavigation

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.trackerexpenses.R
import com.example.trackerexpenses.bottomNavigation.BottomScreen.BottomNavItems
import com.example.trackerexpenses.navigation.RouteApp
import kotlinx.coroutines.launch


@Composable
fun BottomNavbar(
    navController: NavController,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    isVisible: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var bottomNavVisibility by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var selectedPosition by remember { mutableIntStateOf(0) }
    selectedPosition =
        BottomNavItems.indexOfFirst {
            it.route == navController.currentDestination?.route
        }
    selectedPosition = selectedPosition.takeIf { it >= 0 } ?: 0

    LaunchedEffect(navController) {
        onBackPressedDispatcher.addCallback {
            coroutineScope.launch {
                if (navController.previousBackStackEntry == null) {
                    // Handle back press when there's no previous entry, e.g., exit the app.
                    // You can customize this behavior if needed.
                } else {
                    // Handle normal back press
                    navController.popBackStack()
                    // Update the selectedPosition when navigating back
                    val newDestination = navController.currentDestination?.route
                    selectedPosition = BottomNavItems.indexOfFirst { it.route == newDestination }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = bottomNavVisibility,
        content = {
            BottomNavigation(
                backgroundColor = Color.Blue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                BottomNavItems.forEachIndexed { index, navItem ->
                    BottomNavigationItem(
                        selected = (selectedPosition == index),
                        onClick = {
                            selectedPosition = index
                            navController.navigate(navItem.route)
                        },
                        icon = {
                            Icon(
                                painterResource(id = navItem.icon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = if (selectedPosition == index) "." else "",
                                color = Color.White,
                                fontSize = 50.sp
                            )
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.White.copy(0.3f),
                        alwaysShowLabel = true
                    )
                }
            }
        })

    when (navBackStackEntry?.destination?.route) {
        RouteApp.HomeScreen.route,
        RouteApp.TransactionScreen.route,
        RouteApp.AddScreen.route,
        RouteApp.ReceiptScreen.route,
        RouteApp.ProfileScreen.route,
        -> {
            bottomNavVisibility = true
            isVisible(true)
        }

        else -> {
            bottomNavVisibility = false
            isVisible(false)
        }
    }
}


object BottomScreen {
    val BottomNavItems = listOf(
        BottomNavItem(
            RouteApp.HomeScreen.route,
            R.drawable.ic_home
        ),
        BottomNavItem(
            RouteApp.TransactionScreen.route,
            R.drawable.ic_notification
        ),
        BottomNavItem(
            RouteApp.AddScreen.route,
            R.drawable.ic_add
        ),
        BottomNavItem(
            RouteApp.ReceiptScreen.route,
            R.drawable.ic_receipt
        ),
        BottomNavItem(
            RouteApp.ProfileScreen.route,
            R.drawable.ic_profile
        )
    )
}

data class BottomNavItem(
    val route: String, val icon: Int
)