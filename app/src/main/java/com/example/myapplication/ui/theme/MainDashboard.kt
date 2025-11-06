package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize
import com.example.myapplication.AppScreen

@Composable
fun MainDashboard(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    Scaffold(
        content = { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    "Welcome",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.uTitletext,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(60.dp))
                content()
                Button(
                    onClick = {navController.navigate(AppScreen.TypeUser.name)},
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(182.dp)
                        .height(65.dp)
                ) {
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.uTypetext
                    )
                }
            }
        }
    )
}
