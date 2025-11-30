package com.example.myapplication.ui.theme.professorDashb.bookExams

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MainLayout
import androidx.compose.foundation.layout.size
import com.example.myapplication.ui.theme.basicButton
import androidx.compose.material3.OutlinedTextField


@Composable
fun UploadExam(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Exam requirements"

    ) {
        var uploadexam by remember { mutableStateOf("") }

        Text("Upload Exam",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = uploadexam,
            onValueChange = { uploadexam = it },
            placeholder = { Text("") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.importimage),
                    contentDescription = "downloadfile",
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        basicButton(
            onClick = { navController.navigate("ExamReqSent") },
            imageContent = {
                Image(
                    painter = painterResource(id = com.example.myapplication.R.drawable.send_button),
                    contentDescription = "Send Button",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        )
    }}

