package com.example.myapplication.ui.theme.studentDashb

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.uTypetext
import androidx.compose.material3.ButtonDefaults
import com.example.myapplication.ui.theme.MainDashboard
import com.example.myapplication.ui.theme.RealTimeStatusListener

@Composable
fun SDashboard(navController: NavHostController) {
    val context = LocalContext.current
    val statusListener = remember { RealTimeStatusListener(context) }

    DisposableEffect(Unit) {
        statusListener.startListening()
        onDispose {
            statusListener.stopListening()
        }
    }

    MainDashboard(
        navController = navController,
    ) {
        Button(
            onClick = {navController.navigate("StatusViewerScreen")},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .width(297.dp)
                .height(111.dp)
        ) {
            Text(
                text = "Deferral requests",
                style = MaterialTheme.typography.uTypetext
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
        Button(
            onClick = {navController.navigate("ExamDeferralForm")},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .width(297.dp)
                .height(111.dp)
        ) {
            Text(
                text = "Request a Deferral",
                style = MaterialTheme.typography.uTypetext)
        }
        Spacer(modifier = Modifier.height(60.dp))
    }
}