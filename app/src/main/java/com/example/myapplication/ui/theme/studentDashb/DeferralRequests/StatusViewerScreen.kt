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

package com.example.myapplication.ui.theme.studentDashb.DeferralRequests

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.MainLayout

@Composable
fun StatusViewerScreen(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Deferral Requests"

    ){
    }
}