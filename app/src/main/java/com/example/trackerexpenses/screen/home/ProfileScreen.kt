package com.example.trackerexpenses.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trackerexpenses.R
import com.example.trackerexpenses.navigation.RouteApp
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    var isDeletingAccount by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    var emailReset by remember { mutableStateOf(auth.currentUser?.email ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.subtitle1,
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = auth.currentUser?.displayName ?: "No Name",
            style = MaterialTheme.typography.subtitle1,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = emailReset,
            onValueChange = {
                emailReset = it
            },
            label = { Text("Email") },
            textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        navController.navigate(RouteApp.ResetEmailRoute.route)
                    }, tint = Color.White
                )

            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White,
                textColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White
            ),
            singleLine = true,
            enabled = false
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Logout Button
        Button(
            onClick = {
                auth.signOut()
                navController.navigate(RouteApp.LoginRoute.route) {
                    popUpTo(RouteApp.LoginRoute.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Logout", color = Color.White)
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Delete Account Button
        Button(
            onClick = {
                showConfirmationDialog = true
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Delete Account", color = Color.White)
        }

    }
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        isDeletingAccount = true
                        deleteAccount(auth) { success ->
                            isDeletingAccount = false
                            if (success) {
                                navController.navigate(RouteApp.LoginRoute.route) {
                                    popUpTo(RouteApp.LoginRoute.route) { inclusive = true }
                                }
                            }
                        }
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmationDialog = false }) {
                    Text("No")
                }
            }
        )
    }
    if (isDeletingAccount) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

fun deleteAccount(auth: FirebaseAuth, onComplete: (Boolean) -> Unit) {
    val user = auth.currentUser
    user?.delete()?.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            onComplete(true)
        } else {
            onComplete(false)
        }
    }
}
