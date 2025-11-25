/*
------------------------------------------------------------
 File: StatusViewerScreen.kt
 Module: studentDashb/DeferralRequests

 PURPOSE:
    Displays a list of all deferral requests submitted by the student.
    Shows request status (Pending, Approved, Denied) and key details.

 REQUIRED UI:
    - LazyColumn: List of deferral request cards
    - Each card displays:
        • Reason (shortened)
        • Preferred date/time
        • Status indicator
    - Pull-to-refresh or reload button
    - Clickable request cards → open ExamDetailScreen

 FUNCTIONS CALLED (from StudentViewModel):
    - loadStudentRequests(studentId: String)
    - selectRequest(requestId: String)

 STATE OBSERVED:
    - deferralRequests: StateFlow<List<DeferralRequest>>

 NAVIGATION:
    On card click → ExamDetailScreen.kt
------------------------------------------------------------
*/

package com.example.myapplication.ui.theme.studentDashb.deferralRequests

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.MainLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.inboxBox
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun StatusViewerScreen(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Deferral Requests"

    ){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            // This is the layout code for the exam information
            inboxBox(onClick = { navController.navigate("ExamDetailScreen") }) {
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