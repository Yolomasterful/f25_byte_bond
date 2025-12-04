package com.example.myapplication.ui.theme.adminDashb.bookARoom

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
fun RoomBooked(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "",
        showBackArrow = false

    ){
        Spacer(modifier = Modifier.height(50.dp))
        whiteBox{
            Spacer(modifier = Modifier.height(400.dp))
            Text(
                "Room booked",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.uTitletext,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(400.dp))
        }

        Spacer(modifier = Modifier.height(50.dp))

        basicButton(
            onClick = { navController.navigate("dashboard_admin") },
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