package com.example.trackerexpenses.screen.authScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@Composable
fun ResetEmailScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val context = LocalContext.current
    var emailReset by remember {
        mutableStateOf(
            currentUser?.email ?: ""
        )
    }

    val emailError = remember { mutableStateOf(false) }
    val emailUpdateSuccess = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
    val emailErrorMessage = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(20.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController.popBackStack()
                    }, tint = Color.White
            )
            Text(
                text = "Reset Email",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = emailReset,
            onValueChange = {
                emailReset = it
                emailError.value = !isValidEmail(it)
            },
            isError = emailError.value,
            label = { Text("Email", color = Color.White) },
            textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White,
                textColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White
            ),
            singleLine = true
        )
        if (emailError.value) {
            Text(text = emailErrorMessage.value, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Button to Update Email
        Button(
            onClick = {
                when {
                    emailReset.isBlank() -> {
                        emailError.value = true
                        emailErrorMessage.value = "Please enter your email"
                    }
                    !isValidEmail(emailReset) -> {
                        emailError.value = true
                        emailErrorMessage.value = "Invalid email format"
                    }
                    else -> {
                        isLoading.value = true
                        currentUser?.let { user ->
                            updateEmail(user, emailReset) { success, errorMessage ->
                                isLoading.value = false
                                if (success) {
                                    emailUpdateSuccess.value = true
                                    Toast.makeText(context, "Email updated successfully", Toast.LENGTH_SHORT).show()
                                } else {
                                    emailError.value = true
                                    emailErrorMessage.value = "Email not found"
                                }
                            }
                        }
                    }
                }
            },
            enabled = !isLoading.value && emailReset.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading.value) {
                CircularProgressIndicator()
            } else {
                Text(text = "Update Email", color = Color.White)
            }
        }

        if (emailUpdateSuccess.value) {
            Toast.makeText(context, "Email updated successfully", Toast.LENGTH_SHORT).show()
        }

    }
}

fun updateEmail(user: FirebaseUser, newEmail: String, onResult: (Boolean, String?) -> Unit) {
    user.updateEmail(newEmail)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true, null)
            } else {
                onResult(false, task.exception?.message)
            }
        }
}