package com.example.myapplication.ui.theme.studentDashb.requestDeferral

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.SearchDropdown
import com.example.myapplication.ui.theme.basicButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.setValue


@Composable
fun ExamDeferralForm2(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Request a deferral"

    ) {
        Text(
            "Exam Date",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(6.dp))
        SearchDropdown(
            //Add data retrieved infor here of outside this function?
            options = listOf(
                "Calender goes here!"
            )
        ) {
            // add the code to retrieve?
        }

        Spacer(modifier = Modifier.height(25.dp))
        Text(
            "Requested Time",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(6.dp))
        SearchDropdown(
            //Add data retrieved infor here of outside this function?
            options = listOf(
                "08:00 am",
                "08:30 am"
            )
        ) {
            // add the code to retrieve?
        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            "Reason",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        var reason by remember { mutableStateOf("") }
        Column {
            TextField(
                value = reason,
                onValueChange = { newText ->
                    reason = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                placeholder = { Text("Maximum of 200 characters…") },
                singleLine = false,
                maxLines = 6,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            if (reason.length > 200) {
                Text(
                    text = "Minimum 200 characters required (${reason.length}/200)",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }


        Spacer(modifier = Modifier.height(25.dp))
        basicButton(
            onClick = { navController.navigate("RequestSent") },
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




