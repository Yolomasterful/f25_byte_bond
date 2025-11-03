package com.example.myapplication.ui.theme.professorDashb

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.LoginScreen

@Composable
fun ProfessorLogin(navController: NavHostController) {
    LoginScreen(
        navController = navController,
        userType = "professor",
        onLoginSuccess = {
            navController.navigate("professor_dashboard")
        }
    )
}