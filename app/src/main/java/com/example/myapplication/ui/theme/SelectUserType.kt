package com.example.myapplication.ui.theme

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
import androidx.navigation.NavHostController
//import com.example.myapplication.AppScreen


@Composable
fun SelectUserType(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        Image(
            painter = painterResource(id = R.mipmap.mainpage),
            contentDescription = "Logo App",
            modifier = Modifier
                .width(109.dp)
                .height(109.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // STUDENT
        Button(
            onClick = { navController.navigate("login/student") },
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.width(279.dp).height(92.dp)
        ) {
            Text(text = "Student", style = MaterialTheme.typography.uTypetext)
        }

        Spacer(modifier = Modifier.height(50.dp))

        // PROFESSOR
        Button(
            onClick = { navController.navigate("login/professor") },
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.width(279.dp).height(92.dp)
        ) {
            Text(text = "Professor", style = MaterialTheme.typography.uTypetext)
        }

        Spacer(modifier = Modifier.height(50.dp))

        // PROCTOR
        Button(
            onClick = { navController.navigate("login/proctor") },
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.width(279.dp).height(92.dp)
        ) {
            Text(text = "Proctor", style = MaterialTheme.typography.uTypetext)
        }

        Spacer(modifier = Modifier.height(50.dp))

        // ADMIN
        Button(
            onClick = { navController.navigate("login/admin") },
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.width(279.dp).height(92.dp)
        ) {
            Text(text = "Admin", style = MaterialTheme.typography.uTypetext)
        }
    }
}