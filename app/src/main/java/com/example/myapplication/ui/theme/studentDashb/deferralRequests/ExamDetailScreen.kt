/*
------------------------------------------------------------
 File: ExamDetailScreen.kt
 Module: studentDashb/DeferralRequests

 PURPOSE:
    Shows full details for a single deferral request selected by the student.
    Displays approval status, denial reasons, or final exam schedule.

 REQUIRED UI:
    - Reason (full text)
    - Preferred date/time
    - Status badge: Pending / Approved / Denied
    - If Denied: show instructor's denialReason
    - If Approved: show finalDate
    - Back button → returns to StatusViewerScreen

 FUNCTIONS CALLED (from StudentViewModel):
    - selectedRequest: StateFlow<DeferralRequest?>
      (Screen must observe this state)

 NAVIGATION:
    Back → StatusViewerScreen.kt
------------------------------------------------------------
*/

package com.example.myapplication.ui.theme.studentDashb.deferralRequests

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
fun ExamDetailScreen(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "CHANGE TO CLASS NAME"

    ){
        whiteBox{
            //Insert info from data here!
        }

        Spacer(modifier = Modifier.height(40.dp))

        basicButton(
            onClick = { navController.navigate("dashboard_student") },
            imageContent = {
                Image(
                    painter = painterResource(id = R.drawable.home_fill),
                    contentDescription = "Home Button",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
            )
    }
}