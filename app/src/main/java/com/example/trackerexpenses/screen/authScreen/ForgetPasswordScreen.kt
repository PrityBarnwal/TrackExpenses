package com.example.trackerexpenses.screen.authScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trackerexpenses.navigation.RouteApp
import com.example.trackerexpenses.screen.authScreen.authutils.CommonOutlinedTextFieldAuth
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf(false) }
    val isEmailSent = remember { mutableStateOf(false) }
    val isSending = remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Forgot Password",
            style = androidx.compose.material.MaterialTheme.typography.subtitle1,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(20.dp))

        // Email Input
        CommonOutlinedTextFieldAuth(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError.value =!isValidEmail(it)
            },
            isError = emailError.value,
            labelText = "Email",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done, isErrorText = "Invalid email format"
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Send Reset Email Button
        Button(
            onClick = {
                if (!emailError.value && email.value.isNotBlank()) {
                    isSending.value = true
                    sendPasswordResetEmail(auth, email.value) { success ->
                        isSending.value = false
                        isEmailSent.value = success
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSending.value
        ) {
            Text(text = "Send Reset Email", color = Color.White)
        }

        // Show Confirmation Message
        if (isEmailSent.value) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Reset email sent! Check your inbox.", color = Color.Green)
        }

        // Loading Indicator
        if (isSending.value) {
            Spacer(modifier = Modifier.height(10.dp))
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Back to Login Button
        Button(
            onClick = { navController.navigate(RouteApp.LoginRoute.route) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text(text = "Back to Login", color = Color.White)
        }
    }
}

fun sendPasswordResetEmail(auth: FirebaseAuth, email: String, onComplete: (Boolean) -> Unit) {
    auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
        onComplete(task.isSuccessful)
    }
}