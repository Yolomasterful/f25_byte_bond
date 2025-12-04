/*
------------------------------------------------------------
 File: RequestSent.kt
 Module: studentDashb/RequestDeferral

 PURPOSE:
    Confirmation screen shown after a deferral request is successfully
    submitted. Confirms submission and provides next-step actions.

 REQUIRED UI:
    - Success message: “Your deferral request has been submitted”
    - Button: “View My Requests” → takes user to StatusViewerScreen
    - Optional: Icon or illustration for success

 FUNCTIONS CALLED:
    No ViewModel functions required.
    Only triggers navigation actions.

 NAVIGATION:
    Button → StatusViewerScreen.kt
------------------------------------------------------------
*/

package com.example.myapplication.ui.theme.studentDashb.requestDeferral

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.basicButton
import com.example.myapplication.ui.theme.uTitletext
import com.example.myapplication.ui.theme.whiteBox

@Composable
fun RequestSent(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "",
        showBackArrow = false

    ){
        Spacer(modifier = Modifier.height(50.dp))
        whiteBox{
            Spacer(modifier = Modifier.height(400.dp))
            Text(
                "Request Sent",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.uTitletext,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(400.dp))
        }

        Spacer(modifier = Modifier.height(50.dp))

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
