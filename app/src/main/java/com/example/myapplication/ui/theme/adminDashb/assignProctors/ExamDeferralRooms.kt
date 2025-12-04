package com.example.myapplication.ui.theme.adminDashb.assignProctors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.inboxBox

@Composable
fun ExamDeferralRooms(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Deferral Requests"

    ){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            // This is the layout code for the exam information
            inboxBox(onClick = { navController.navigate("AssignProctor") }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Room number")
                    }
                    Column {
                        Text("Date of availability")
                    }
                }
            }
        }
    }
}