package com.example.myapplication.ui.theme.professorDashb

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.uTypetext
import com.example.myapplication.ui.theme.uTitletext
import androidx.compose.material3.ButtonDefaults



@Composable

fun PfDashboard(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Spacer(modifier = Modifier.height(177.dp))
        Text("Welcome",
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.uTitletext,
            textAlign = TextAlign.Center
        )
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
                text = "Inbox",
                style = MaterialTheme.typography.uTypetext
            )
        }
        Spacer(modifier = Modifier.height(61.dp))
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
                text = "Book Exams",
                style = MaterialTheme.typography.uTypetext)
        }

    }
}
