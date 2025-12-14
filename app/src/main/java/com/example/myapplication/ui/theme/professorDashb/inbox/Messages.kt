package com.example.myapplication.ui.theme.professorDashb.inbox

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.whiteBox

@Composable
fun Messages(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Inbox",


    ){
        Spacer(modifier = Modifier.height(20.dp))
<<<<<<< HEAD
        MessageRequestsTabs(defaultTab = 0, navController)
=======
        MessageRequestsTabs(navController = navController)
>>>>>>> upstream/main

    }
}