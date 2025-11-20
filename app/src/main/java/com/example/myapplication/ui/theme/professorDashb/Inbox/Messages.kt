package com.example.myapplication.ui.theme.professorDashb.Inbox

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.MainLayout

@Composable
fun Messages(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Inbox"

    ){
    }
}