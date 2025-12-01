package com.example.myapplication.ui.theme

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.data.firebase.FirebaseAuthService
import com.example.myapplication.data.firebase.AuthResult
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    userType: String = "",
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // -------- TITLE --------
        Text(
            text = "Login",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 94.dp),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Hello $userType",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .background(MaterialTheme.colorScheme.surface)
                .height(525.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(29.dp)
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                // -------- EMAIL --------
                Text(
                    "Email:",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(6.dp))

                AppwhiteTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholderText = "",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(25.dp))

                // -------- PASSWORD --------
                Text(
                    "Password:",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(6.dp))

                AppwhiteTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholderText = "",
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = painterResource(
                                    id = if (passwordVisible)
                                        android.R.drawable.ic_menu_close_clear_cancel
                                    else
                                        android.R.drawable.ic_menu_view
                                ),
                                contentDescription = "Toggle password"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // -------- LOGIN BUTTON --------
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Email & password required", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val authService = FirebaseAuthService()

                        coroutineScope.launch {
                            when (val result = authService.login(email, password)) {
                                is AuthResult.Success -> {
                                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()

                                    // Route based on userType
                                    when (userType.lowercase()) {
                                        "student" -> navController.navigate("dashboard_student")
                                        "professor" -> navController.navigate("dashboard_professor")
                                        "proctor" -> navController.navigate("dashboard_proctor")
                                        "admin" -> navController.navigate("dashboard_admin")
                                        else -> Toast.makeText(context, "Unknown role", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                is AuthResult.Error -> {
                                    Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .width(179.dp)
                        .height(54.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Login")
                }
            }
        }
    }
}

