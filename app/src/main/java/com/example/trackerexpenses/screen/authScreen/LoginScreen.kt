package com.example.trackerexpenses.screen.authScreen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trackerexpenses.R
import com.example.trackerexpenses.navigation.RouteApp
import com.example.trackerexpenses.screen.authScreen.authutils.CommonOutlinedTextFieldAuth
import com.example.trackerexpenses.screen.authScreen.authutils.firebaseAuthWithGoogle
import com.example.trackerexpenses.screen.authScreen.authutils.loginWithEmailPassword
import com.example.trackerexpenses.screen.home.CommonOutlinedTextField
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

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
    val formErrorMessage = remember { mutableStateOf<String?>(null) }
    BackHandler {
        (navController.context as? Activity)?.finish()
    }

    val isFormValid by remember {
        derivedStateOf {
            !emailError.value && !passwordError.value && email.value.isNotBlank() && password.value.isNotBlank()
        }
    }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)


    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(Exception::class.java)
                account?.let {
                    firebaseAuthWithGoogle(it, navController, auth)
                }
            } catch (e: Exception) {
                formErrorMessage.value = "Google sign-in failed: ${e.message}"
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.subtitle1,
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        CommonOutlinedTextFieldAuth(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError.value = !isValidEmail(it)
            },
            isError = emailError.value,
            labelText = "Email",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next, isErrorText = "Invalid email format"
        )

        Spacer(modifier = Modifier.height(10.dp))
        CommonOutlinedTextFieldAuth(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordError.value = !isValidPassword(it)
            },
            isError = passwordError.value,
            labelText = "Password",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            isErrorText = "Password must start with a capital letter, contain at least 8 characters, 1 number, and 1 special character"
        )

        Spacer(modifier = Modifier.height(10.dp))

        formErrorMessage.value?.let {
            Text(text = it, color = Color.Red)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(
            onClick = {
                if (isFormValid) {
                    loginWithEmailPassword(
                        email = email.value,
                        password = password.value,
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
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                disabledContainerColor = Color.LightGray
            ),
            enabled = isFormValid
        ) {
            Text(text = "Login", color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Forget Password",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(RouteApp.ForgotPasswordRoute.route) })

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
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text(text = "Sign in with Google", color = Color.White)
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}


