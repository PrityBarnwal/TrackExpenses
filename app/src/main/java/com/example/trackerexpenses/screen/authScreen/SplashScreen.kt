package com.example.trackerexpenses.screen.authScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trackerexpenses.R
import com.example.trackerexpenses.navigation.RouteApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(1000)
//        navController.navigate(RouteApp.LoginRoute.route)
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            // User is signed in, navigate to the Home screen
            navController.navigate(RouteApp.HomeScreen.route) {
                popUpTo(RouteApp.SplashRoute.route) { inclusive = true }
            }
        } else {
            // User is not signed in, navigate to the Login screen
            navController.navigate(RouteApp.LoginRoute.route) {
                popUpTo(RouteApp.SplashRoute.route) { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp)
        )
    }
}