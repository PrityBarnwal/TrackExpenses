package com.example.trackerexpenses.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.trackerexpenses.screen.authScreen.CreateAccountScreen
import com.example.trackerexpenses.screen.authScreen.ForgotPasswordScreen
import com.example.trackerexpenses.screen.authScreen.LoginScreen
import com.example.trackerexpenses.screen.authScreen.ResetEmailScreen
import com.example.trackerexpenses.screen.authScreen.SplashScreen
import com.example.trackerexpenses.screen.home.AddScreen
import com.example.trackerexpenses.screen.home.HomeScreen
import com.example.trackerexpenses.screen.home.ProfileScreen
import com.example.trackerexpenses.screen.home.ReceiptScreen
import com.example.trackerexpenses.screen.home.TransactionScreen


fun NavGraphBuilder.authNavGraph(
    navController: NavController,
) {
    navigation(startDestination = RouteApp.SplashRoute.route, route = GraphConstant.AUTH_GRAPH) {
        composable(route = RouteApp.SplashRoute.route) {
            SplashScreen(navController)
        }
        composable(route = RouteApp.LoginRoute.route) {
            LoginScreen(navController = navController)
        }
        composable(route = RouteApp.CreateAccountRoute.route) {
            CreateAccountScreen(navController = navController)
        }
        composable(route = RouteApp.ForgotPasswordRoute.route) {
            ForgotPasswordScreen(navController = navController)
        }
        composable(route = RouteApp.ResetEmailRoute.route) {
            ResetEmailScreen(navController = navController)
        }
    }
}

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
) {
    navigation(startDestination = RouteApp.HomeScreen.route, route = GraphConstant.HOME_GRAPH) {
        composable(route = RouteApp.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = RouteApp.TransactionScreen.route) {
            TransactionScreen(navController)
        }
        composable(route = RouteApp.AddScreen.route) {
            AddScreen(navController)
        }
        composable(route = RouteApp.ReceiptScreen.route) {
            ReceiptScreen(navController)
        }
        composable(route = RouteApp.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = GraphConstant.ROOT_GRAPH,
        startDestination = GraphConstant.AUTH_GRAPH
    ) {

        authNavGraph(navController)
        homeNavGraph(navController)
    }
}