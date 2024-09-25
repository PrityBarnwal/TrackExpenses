package com.example.trackerexpenses.screen.authScreen.authutils

import android.util.Log
import androidx.navigation.NavController
import com.example.trackerexpenses.navigation.RouteApp
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest


fun loginWithEmailPassword(
    email: String,
    password: String,
    onResult: (Boolean, String?) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true, null) // Successful login
            } else {
                onResult(false, task.exception?.message) // Login failed, return error message
            }
        }
}

fun firebaseAuthWithGoogle(
    account: GoogleSignInAccount,
    navController: NavController,
    auth: FirebaseAuth
) {
    val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate(RouteApp.HomeScreen.route) {
                    popUpTo(RouteApp.LoginRoute.route) { inclusive = true }
                }
            } else {
                Log.w("GoogleSignIn", "signInWithCredential:failure", task.exception)
            }
        }
}


fun createUserWithEmailPassword(
    email: String,
    password: String,
    name: String,
    onResult: (Boolean, String?) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()

                user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener { profileTask ->
                        if (profileTask.isSuccessful) {
                            onResult(true, null) // Success
                        } else {
                            onResult(false, profileTask.exception?.message)
                        }
                    }
            } else {
                onResult(false, task.exception?.message)
            }
        }
}