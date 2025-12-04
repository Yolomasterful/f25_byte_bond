package com.example.myapplication.ui.theme.professorDashb.inbox.requests

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.uTypetext
import com.example.myapplication.ui.theme.whiteBox

@Composable
fun ChoiceDetails(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Student Name",
        showBackArrow = false

    ){
        Spacer(modifier = Modifier.height(8.dp))

        Text("is requesting an exam deferral for",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        whiteBox{
            //Insert Date from data here!
            Text("Date goes here",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Reason:",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        whiteBox{
            //Insert reason from data here!
            Text("Reason goes here",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Accept request?",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {navController.navigate("ConfirmChoice")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(182.dp)
                    .height(65.dp)
            ) {
                Text(
                    text = "Yes",
                    style = MaterialTheme.typography.uTypetext
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {navController.navigate("RequestsPage")},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(182.dp)
                    .height(65.dp)
            ) {
                Text(
                    text = "No",
                    style = MaterialTheme.typography.uTypetext
                )
            }

        }


    }
}