package com.example.trackerexpenses.screen.authScreen

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trackerexpenses.navigation.RouteApp


@Composable
fun CreateAccountScreen(navController: NavController) {

    val nameCreate = remember {
        mutableStateOf("")
    }
    val emailCreate = remember {
        mutableStateOf("")
    }
    val passwordCreate = remember {
        mutableStateOf("")
    }

    val nameError = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }

    val isFormValid = remember {
        derivedStateOf {
            !nameError.value && !emailError.value && !passwordError.value && nameCreate.value.isNotBlank() && emailCreate.value.isNotBlank() && passwordCreate.value.isNotBlank()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        OutlinedTextField(
            value = nameCreate.value,
            onValueChange = { nameCreate.value = it },
            label = { Text("Name") },
            isError = nameError.value,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ), modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = emailCreate.value,
            onValueChange = {
                emailCreate.value = it
                emailError.value = !isValidEmail(it)
            },
            isError = emailError.value,
            label = { Text("Email") },
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
            value = passwordCreate.value,
            onValueChange = {
                passwordCreate.value = it
                passwordError.value = !isValidPassword(it)
            },
            isError = passwordError.value,
            label = { Text("Password") },
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
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                disabledContainerColor = Color.LightGray
            ), enabled = isFormValid.value

        ) {
            Text(text = "Create Account", color = Color.White)
        }
    }
}