package com.example.myapplication.ui.theme.proctorDashb

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.uTypetext
import androidx.compose.material3.ButtonDefaults
import com.example.myapplication.ui.theme.MainDashboard

@Composable
fun PDashboard(navController: NavHostController) {
    MainDashboard(navController = navController) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .width(297.dp)
                    .height(111.dp)
            ) {
                Text(
                    text = "Inbox",
                    style = MaterialTheme.typography.uTypetext
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .width(297.dp)
                    .height(111.dp)
            ) {
                Text(
                    text = "Availability",
                    style = MaterialTheme.typography.uTypetext
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .width(297.dp)
                    .height(111.dp)
            ) {
                Text(
                    text = "My Schedule",
                    style = MaterialTheme.typography.uTypetext
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}