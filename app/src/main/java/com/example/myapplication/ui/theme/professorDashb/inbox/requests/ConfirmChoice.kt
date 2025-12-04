package com.example.myapplication.ui.theme.professorDashb.inbox.requests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.basicButton
import com.example.myapplication.ui.theme.uTitletext
import com.example.myapplication.ui.theme.uTypetext
import com.example.myapplication.ui.theme.whiteBox

@Composable
fun ConfirmChoice(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Student Name",
        showBackArrow = false

    ){
        whiteBox{
            Column() {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Information Summary",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.uTitletext,
                    textAlign = TextAlign.Center
                )

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

                Spacer(modifier = Modifier.height(20.dp))
            }

        }


    }
}
