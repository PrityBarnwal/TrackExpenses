package com.example.trackerexpenses.screen.authScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trackerexpenses.navigation.RouteApp
import com.example.trackerexpenses.screen.authScreen.authutils.CommonOutlinedTextFieldAuth
import com.example.trackerexpenses.screen.authScreen.authutils.createUserWithEmailPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


@Composable
fun CreateAccountScreen(navController: NavController) {

    var nameCreate by remember {
        mutableStateOf("")
    }
    var emailCreate by remember {
        mutableStateOf("")
    }
    var passwordCreate by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember { mutableStateOf("") }

    val nameError = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    val confirmPasswordError = remember { mutableStateOf(false) }
    val formErrorMessage = remember { mutableStateOf<String?>(null) }


    val isFormValid by remember {
        derivedStateOf {
            !nameError.value &&
                    !emailError.value &&
                    !passwordError.value &&
                    !confirmPasswordError.value &&
                    nameCreate.isNotBlank() &&
                    emailCreate.isNotBlank() &&
                    passwordCreate.isNotBlank() &&
                    confirmPassword.isNotBlank() &&
                    passwordCreate == confirmPassword
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.subtitle1,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        CommonOutlinedTextFieldAuth(
            value = nameCreate,
            onValueChange = {
                nameCreate = it
            },
            isError = nameError.value,
            labelText = "Name",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next, isErrorText = "Please Enter Name"
        )

        Spacer(modifier = Modifier.height(10.dp))

        CommonOutlinedTextFieldAuth(
            value = emailCreate,
            onValueChange = {
                emailCreate = it
                emailError.value = !isValidEmail(it)
            },
            isError = emailError.value,
            labelText = "Email",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next, isErrorText = "Invalid email format"
        )

        Spacer(modifier = Modifier.height(10.dp))

        CommonOutlinedTextFieldAuth(
            value = passwordCreate,
            onValueChange = {
                passwordCreate = it
                passwordError.value = !isValidPassword(it)
            },
            isError = passwordError.value,
            labelText = "Password",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
            isErrorText = "Password must start with a capital letter, contain at least 8 characters, 1 number, and 1 special character"
        )

        Spacer(modifier = Modifier.height(10.dp))

        CommonOutlinedTextFieldAuth(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError.value = passwordCreate != it
            },
            isError = confirmPasswordError.value,
            labelText = "Confirm Password",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done, isErrorText = "Passwords do not match"
        )

        Spacer(modifier = Modifier.height(10.dp))
        formErrorMessage.value?.let {
            Text(text = it, color = Color.Red)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(
            onClick = {
                if (isFormValid) {
                    createUserWithEmailPassword(
                        email = emailCreate,
                        password = passwordCreate,
                        name = nameCreate,
                        onResult = { isSuccess, message ->
                            if (isSuccess) {
                                navController.navigate(RouteApp.HomeScreen.route)
                            } else {
                                formErrorMessage.value = message
                            }
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                disabledContainerColor = Color.LightGray
            ), enabled = isFormValid

        ) {
            Text(text = "Create Account", color = Color.White)
        }
    }
}
