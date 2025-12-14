package com.example.myapplication.ui.theme.professorDashb.bookExams

import android.widget.Toast
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.firebase.FirestoreResult
import com.example.myapplication.data.firebase.FirestoreService
import com.example.myapplication.ui.theme.AppwhiteTextField
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.basicButton
import kotlinx.coroutines.launch

@Composable
fun ExamReqForm(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val firestoreService = remember { FirestoreService() }

    var roomNum by remember { mutableStateOf("") }
    var timeAllowed by remember { mutableStateOf("") }
    var materialsNeeded by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    MainLayout(
        navController = navController,
        pageName = "Exam requirements"
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    "Room preference:",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(6.dp))
                AppwhiteTextField(
                    value = roomNum,
                    onValueChange = { roomNum = it },
                    placeholderText = "e.g., Room 301 or Any ground floor room",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Time allowed:",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(6.dp))
                AppwhiteTextField(
                    value = timeAllowed,
                    onValueChange = { timeAllowed = it },
                    placeholderText = "e.g., 2 hours, 90 minutes",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Materials needed:",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(6.dp))
                AppwhiteTextField(
                    value = materialsNeeded,
                    onValueChange = { materialsNeeded = it },
                    placeholderText = "e.g., Calculator, scantron sheets, blue books",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Notes:",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(6.dp))
                AppwhiteTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholderText = "Any additional information or special requirements",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )

                Spacer(modifier = Modifier.height(20.dp))

                basicButton(
                    onClick = {
                        // Validation
                        when {
                            roomNum.isBlank() -> {
                                Toast.makeText(
                                    context,
                                    "Please provide room preference",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            timeAllowed.isBlank() -> {
                                Toast.makeText(
                                    context,
                                    "Please specify time allowed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            materialsNeeded.isBlank() -> {
                                Toast.makeText(
                                    context,
                                    "Please list materials needed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                // Save to Firestore
                                isLoading = true
                                coroutineScope.launch {
                                    when (val result = firestoreService.saveExamRequest(
                                        roomPreference = roomNum,
                                        timeAllowed = timeAllowed,
                                        materialsNeeded = materialsNeeded,
                                        notes = notes
                                    )) {
                                        is FirestoreResult.Success -> {
                                            isLoading = false
                                            Toast.makeText(
                                                context,
                                                "Exam request submitted successfully!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            navController.navigate("UploadExam") {
                                                popUpTo("ExamReqForm") { inclusive = true }
                                            }
                                        }
                                        is FirestoreResult.Error -> {
                                            isLoading = false
                                            Toast.makeText(
                                                context,
                                                "Error: ${result.message}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            }
                        }
                    },
                    imageContent = {
                        Image(
                            painter = painterResource(id = R.drawable.send_button),
                            contentDescription = "Send Button",
                            modifier = Modifier.size(40.dp),
                        )
                    }
                )
            }
        }
    }
}