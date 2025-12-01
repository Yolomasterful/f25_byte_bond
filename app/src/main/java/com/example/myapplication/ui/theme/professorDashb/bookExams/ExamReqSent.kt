package com.example.myapplication.ui.theme.professorDashb.bookExams

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.basicButton
import com.example.myapplication.ui.theme.whiteBox

@Composable
fun ExamReqSent(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Exam Requirements Sent"

    ) {
        whiteBox{

            Text("Data goes here")

        }

        Spacer(modifier = Modifier.height(40.dp))
        basicButton(
            onClick = { navController.navigate("dashboard_professor") },
            imageContent = {
                Image(
                    painter = painterResource(id = R.drawable.home_fill),
                    contentDescription = "Home Button",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        )
    }}