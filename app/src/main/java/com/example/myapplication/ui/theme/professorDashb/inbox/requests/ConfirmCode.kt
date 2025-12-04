package com.example.myapplication.ui.theme.professorDashb.inbox.requests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.AppwhiteTextField
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.uTitletext
import com.example.myapplication.ui.theme.uTypetext
import com.example.myapplication.ui.theme.whiteBox

@Composable
fun ConfirmCode(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "",
        showBackArrow = false

    ){
        var codeNum by remember { mutableStateOf("") }
        whiteBox {
            Column() {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Type in code to confirm",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.uTitletext,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))


                AppwhiteTextField(
                    // The email box details
                    value = codeNum,
                    onValueChange = { codeNum = it },
                    placeholderText = "",
                    modifier = Modifier
                        .width(230.dp)
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(182.dp)
                        .height(65.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = { navController.navigate("dashboard_admin") },
                ) {
                    Text(
                        text = "Confirm",
                        style = MaterialTheme.typography.uTypetext
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Info. goes here
            }
        }
    }
}