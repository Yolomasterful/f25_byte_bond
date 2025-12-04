package com.example.myapplication.ui.theme.proctorDashb.availability

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.inboxBox
import com.example.myapplication.ui.theme.whiteBox

@Composable
fun ViewSchedule(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "My Availability",
        onBackClick = { navController.navigate("dashboard_proctor")}

    ){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            // This is the layout code for the exam information
            inboxBox(onClick = { navController.navigate("SetAvailability") }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        //Add data for Class name here
                        //Add Data for Date here
                    }
                    Column {
                        //Add data for status
                        //Add Data for classroom name
                    }
                }
            }
        }
    }
}