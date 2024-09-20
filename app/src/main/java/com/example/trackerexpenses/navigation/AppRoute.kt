package com.example.trackerexpenses.navigation

sealed class RouteApp(val route: String) {
    object SplashRoute : RouteApp("splash_screen_route")
    object LoginRoute : RouteApp("login_screen_route")
    object CreateAccountRoute : RouteApp("create_account_route")
    object ResetPassword : RouteApp("reset_password")
    object ForgotPasswordRoute : RouteApp("forgot_password")
    object ResetEmailSent : RouteApp("email_sent")
    object SetProfile : RouteApp("set_profile")
    object OtpScreen : RouteApp("otp_screen_route")


    object HomeScreen : RouteApp("home_screen_route")
    object RemainderScreen : RouteApp("remainder_screen_route")
    object AddScreen : RouteApp("add_screen_route")
    object ReceiptScreen : RouteApp("receipt_screen_route")
    object ProfileScreen : RouteApp("profile_screen_route")
}