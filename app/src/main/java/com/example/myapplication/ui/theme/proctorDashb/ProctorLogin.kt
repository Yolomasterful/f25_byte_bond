package com.example.myapplication.ui.theme.proctorDashb

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.LoginScreen

@Composable
fun ProctorLogin(navController: NavHostController) {
    LoginScreen(
        navController = navController,
        userType = "proctor",
        onLoginSuccess = {
            navController.navigate("proctor_dashboard")
        }
    )
}