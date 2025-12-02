package com.example.myapplication.ui.theme.proctorDashb.inbox

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.MainInbox
import com.example.myapplication.ui.theme.MainLayout

@Composable
fun InboxPage(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Inbox"

    ){
        Spacer(modifier = Modifier.height(20.dp))
        MainInbox(navController = navController)
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Messages go here")
        }
    }
}