package com.example.myapplication.ui.theme.professorDashb.bookExams

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.AppwhiteTextField
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.basicButton

@Composable
fun ExamReqForm(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Exam requirements"

    ){


        val context = LocalContext.current
        var roomNum by remember { mutableStateOf("") }
        var timeAllowed by remember { mutableStateOf("") }
        var MaterialsNeeded by remember { mutableStateOf("") }
        var notes by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(40.dp))
        Text("Room preference:",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(6.dp))
        AppwhiteTextField(
            // The email box details
            value = roomNum,
            onValueChange = { roomNum = it },
            placeholderText = "",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text("Time allowed:",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(6.dp))
        AppwhiteTextField(
            // The email box details
            value = timeAllowed,
            onValueChange = { timeAllowed = it },
            placeholderText = "",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text("Materials needed:",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(6.dp))
        AppwhiteTextField(
            // The email box details
            value = MaterialsNeeded,
            onValueChange = { MaterialsNeeded = it },
            placeholderText = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text("Notes:",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(6.dp))
        AppwhiteTextField(
            // The email box details
            value = notes,
            onValueChange = { notes = it },
            placeholderText = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(20.dp))

        basicButton(
            onClick = { navController.navigate("UploadExam") },
            imageContent = {
                Image(
                    painter = painterResource(id = R.drawable.send_button),
                    contentDescription = "Send Button",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        )

    }
}