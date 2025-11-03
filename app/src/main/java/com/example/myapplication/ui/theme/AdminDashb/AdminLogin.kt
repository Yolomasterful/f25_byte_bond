package com.example.myapplication.ui.theme.AdminDashb

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.LoginScreen

@Composable
fun AdminLogin(navController: NavHostController) {
    LoginScreen(
        navController = navController,
        userType = "admin",
        onLoginSuccess = {
            navController.navigate("admin_dashboard")
        }
    )
}