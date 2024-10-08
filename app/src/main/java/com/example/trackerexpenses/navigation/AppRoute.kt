package com.example.trackerexpenses.navigation

sealed class RouteApp(val route: String) {
    object SplashRoute : RouteApp("splash_screen_route")
    object LoginRoute : RouteApp("login_screen_route")
    object CreateAccountRoute : RouteApp("create_account_route")
    object ResetPassword : RouteApp("reset_password")
    object ForgotPasswordRoute : RouteApp("forgot_password")
    object ResetEmailRoute : RouteApp("email_reset")
    object SetProfile : RouteApp("set_profile")
    object OtpScreen : RouteApp("otp_screen_route")


    object HomeScreen : RouteApp("home_screen_route")
    object TransactionScreen : RouteApp("remainder_screen_route")
    object ReceiptScreen : RouteApp("receipt_screen_route")
    object ProfileScreen : RouteApp("profile_screen_route")

    object AddTransactionScreen : RouteApp("add_transaction_screen_route")
    object AddIncomeScreen : RouteApp("add_income_screen_route")
}