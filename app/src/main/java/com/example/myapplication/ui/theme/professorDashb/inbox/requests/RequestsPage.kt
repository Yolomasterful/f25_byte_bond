package com.example.myapplication.ui.theme.professorDashb.inbox.requests

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.professorDashb.inbox.MessageRequestsTabs

@Composable
fun RequestsPage(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Inbox",
        onBackClick = { navController.navigate("dashboard_professor")}

    ){
        Spacer(modifier = Modifier.height(20.dp))
        MessageRequestsTabs(
            defaultTab = 1,
            navController)

    }
}