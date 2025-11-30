package com.example.myapplication.ui.theme

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Color

@Composable
// Function for the login screen
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    userType: String = "",
    onLoginSuccess: () -> Unit = {}
) {
    Column {
        Text("Login",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp, top = 94.dp, end=0.dp, bottom = 14.dp),
            style = MaterialTheme.typography.uTitletext,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Hello $userType",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp, top = 0.dp, end=0.dp, bottom = 40.dp),
//            style = MaterialTheme.typography.uTitletext,
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )

    }

    // Variables must be defined before using them.
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }


    // Structure: Have the username, password, and login button in the same section
    Box(
        modifier = Modifier
            .padding(start = 20.dp, top = 228.dp, end=20.dp, bottom = 0.dp)
            .background(MaterialTheme.colorScheme.surface)
            .height(525.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(29.dp)
        ) {
            backArrow(
                navController = navController,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(40.dp))
            Text("Email:",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start)
            Spacer(modifier = Modifier.height(6.dp))
            AppwhiteTextField(
                // The email box details
                value = email,
                onValueChange = { email = it },
                placeholderText = "",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            // A space to make the layout pleasing to the eyes.
            Spacer(modifier = Modifier.height(25.dp))
            Text("Password:",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start)
            Spacer(modifier = Modifier.height(6.dp))
            AppwhiteTextField(
                // The password box details
                value = password,
                onValueChange = { password = it },
                placeholderText = "",
                modifier = Modifier.fillMaxWidth(),
                // From here, it contains the code for blocking the password.
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

                // The eye icon that allows the user to select whether the password is visible or not.
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) android.R.drawable.ic_menu_close_clear_cancel else android.R.drawable.ic_menu_view),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

            // Another spacer to make the layout pleasing to the eyes.
            Spacer(modifier = Modifier.height(40.dp))

            // Alast, the code for the login button.
            Button(
                onClick = {onLoginSuccess()},
                modifier = Modifier
                    .width(179.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(size = 20.dp),

            ) {
                Text("Login")
            }
        }
    }
}

