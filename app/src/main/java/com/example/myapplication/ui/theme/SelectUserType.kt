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
import com.example.myapplication.AppScreen


@Composable

fun SelectUserType(
    navController: NavHostController,
    //onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Image(
            painter = painterResource(id = R.mipmap.mainpage),
            contentDescription = "Logo App",
            modifier = Modifier
                .width(109.dp)
                .height(109.dp)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Button(
            onClick = {navController.navigate("login_student")},
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .width(279.dp)
                .height(92.dp)
        ) {
            Text(
                text = "Student",
                style = MaterialTheme.typography.uTypetext
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {navController.navigate("login_professor")},
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .width(279.dp)
                .height(92.dp)
        ) {
            Text(
                text = "Professor",
                style = MaterialTheme.typography.uTypetext)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {navController.navigate("proctor_dashboard")},
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .width(279.dp)
                .height(92.dp)
        ) {
            Text(
                text = "Proctor",
                style = MaterialTheme.typography.uTypetext
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {navController.navigate("login_admin")},
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .width(279.dp)
                .height(92.dp)
        ) {
            Text(
                text = "Admin",
                style = MaterialTheme.typography.uTypetext
            )
        }
    }
}


