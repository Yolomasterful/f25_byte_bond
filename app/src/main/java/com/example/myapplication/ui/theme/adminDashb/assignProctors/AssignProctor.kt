package com.example.myapplication.ui.theme.adminDashb.assignProctors

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
fun AssignProctor(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Room #Number",
        showBackArrow = false

    ){
        Spacer(modifier = Modifier.height(8.dp))

        Text("Date of Exam",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("11:00am - 1:00pm",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("Added Proctors:",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        whiteBox{
            //Insert Date from data here!
            Text("Assigned proctor information",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Available Proctors:",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        whiteBox{
            //Insert reason from data here!
            Text("Available proctor information",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .width(182.dp)
                .height(65.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { navController.navigate("ConfirmCode") },
        ) {
            Text(
                text = "Confirm",
                style = MaterialTheme.typography.uTypetext
            )
        }


    }
}

