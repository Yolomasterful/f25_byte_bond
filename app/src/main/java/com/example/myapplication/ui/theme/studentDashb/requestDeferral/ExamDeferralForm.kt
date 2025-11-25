/*
------------------------------------------------------------
 File: ExamDeferralForm.kt
 Module: studentDashb/RequestDeferral

 PURPOSE:
    Screen where a student fills in the deferral request form.
    Collects reason, preferred exam date, and preferred time.
    Validates inputs and submits the request through ViewModel.

 REQUIRED UI:
    - TextField: Reason for deferral
    - Date Picker: Preferred exam date
    - Time Picker: Preferred exam time
    - Submit Button
    - Loading indicator (during submit)
    - Error message text (if submission fails)

 FUNCTIONS CALLED (from StudentViewModel):
    - updateReason(reason: String)
    - updatePreferredDate(date: String)
    - updatePreferredTime(time: String)
    - submitDeferralForm(onSuccess: () -> Unit, onError: (String) -> Unit)

 NAVIGATION:
    On success → navigate to RequestSent.kt
------------------------------------------------------------
*/

package com.example.myapplication.ui.theme.studentDashb.requestDeferral

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.SearchDropdown
import com.example.myapplication.ui.theme.basicButton

@Composable
fun ExamDeferralForm(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Request a deferral"

    ){
        Spacer(modifier = Modifier.height(40.dp))
        Text("Course List",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(6.dp))
        SearchDropdown(
            //Add data retrieved infor here of outside this function?
            options = listOf(
                "CMPT 280",
                "CMPT 395"
            )
        ) {
            // add the code to retrieve?
        }
        Spacer(modifier = Modifier.height(40.dp))
        basicButton(
            onClick = {navController.navigate("ExamDeferralForm2")},
            imageContent = {
                Image(
                    painter = painterResource(id = R.drawable.send_button),
                    contentDescription = "Send Button",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        )

    }
}