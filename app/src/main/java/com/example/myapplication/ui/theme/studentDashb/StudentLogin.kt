package com.example.myapplication.ui.theme.studentDashb

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.LoginScreen

@Composable
fun StudentLogin(navController: NavHostController) {
    LoginScreen(
        navController = navController,
        userType = "Student",
        onLoginSuccess = {
            navController.navigate("student_dashboard")
        }
    )
}