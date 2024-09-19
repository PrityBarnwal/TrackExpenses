package com.example.trackerexpenses.screen.authScreen

import android.util.Patterns
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trackerexpenses.navigation.RouteApp

@Composable
fun LoginScreen(navController: NavController) {

    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }

    val isFormValid = remember {
        derivedStateOf {
            !emailError.value && !passwordError.value && email.value.isNotBlank() && password.value.isNotBlank()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError.value = !isValidEmail(it)
            },
            isError = emailError.value,
            label = { Text("Email") },
            singleLine = true,
            textStyle =
            TextStyle(color = Color.White, fontSize = 12.sp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ), modifier = Modifier.fillMaxWidth()
        )
        if (emailError.value) {
            Text(
                text = "Invalid email format",
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordError.value = !isValidPassword(it)
            },
            isError = passwordError.value,
            label = { Text("Password") },
            singleLine = true,
            textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ), modifier = Modifier.fillMaxWidth()
        )
        if (passwordError.value) {
            Text(
                text = "Password must start with a capital letter, contain at least 8 characters, 1 number, and 1 special character",
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (isFormValid.value) {
                    navController.navigate(RouteApp.HomeScreen.route)
                }
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                disabledContainerColor = Color.LightGray
            ),
            enabled = isFormValid.value
        ) {
            Text(text = "Login", color = Color.White)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { navController.navigate(RouteApp.CreateAccountRoute.route) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(text = "SignIn", color = Color.White)
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    val passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[A-Za-z0-9!@#\$%^&*]{8,}$"
    return Regex(passwordPattern).matches(password)
}