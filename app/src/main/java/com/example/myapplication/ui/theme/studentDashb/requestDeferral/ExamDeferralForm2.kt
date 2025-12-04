package com.example.myapplication.ui.theme.studentDashb.requestDeferral

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.firebase.FirestoreResult
import com.example.myapplication.data.firebase.FirestoreService
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.SearchDropdown
import com.example.myapplication.ui.theme.basicButton
import kotlinx.coroutines.launch

@Composable
fun ExamDeferralForm2(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val firestoreService = remember { FirestoreService() }

    var examDate by remember { mutableStateOf("") }
    var requestedTime by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    MainLayout(
        navController = navController,
        pageName = "Request a deferral"
    ) {
        // Show loading overlay
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column {
                // Exam Date
                Text(
                    "Exam Date",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(6.dp))
                SearchDropdown(
                    options = listOf(
                        "2025-01-15",
                        "2025-01-20",
                        "2025-01-25",
                        "2025-02-01",
                        "2025-02-10"
                    )
                ) { selectedDate ->
                    examDate = selectedDate
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Requested Time
                Text(
                    "Requested Time",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(6.dp))
                SearchDropdown(
                    options = listOf(
                        "08:00 AM", "08:30 AM", "09:00 AM", "09:30 AM",
                        "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM",
                        "12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM", "02:00 PM"
                    )
                ) { selectedTime ->
                    requestedTime = selectedTime
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Reason
                Text(
                    "Reason",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                TextField(
                    value = reason,
                    onValueChange = { newText ->
                        if (newText.length <= 200) reason = newText
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = { Text("Maximum of 200 characters…") },
                    singleLine = false,
                    maxLines = 6,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                Text(
                    text = "${reason.length}/200 characters",
                    color = if (reason.length > 200) Color.Red else Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Submit Button
                basicButton(
                    onClick = {
                        // Validation
                        when {
                            examDate.isBlank() -> {
                                Toast.makeText(context, "Please select an exam date", Toast.LENGTH_SHORT).show()
                            }
                            requestedTime.isBlank() -> {
                                Toast.makeText(context, "Please select a requested time", Toast.LENGTH_SHORT).show()
                            }
                            reason.isBlank() -> {
                                Toast.makeText(context, "Please provide a reason", Toast.LENGTH_SHORT).show()
                            }
                            reason.length < 10 -> {
                                Toast.makeText(context, "Reason must be at least 10 characters", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                // Save to Firestore
                                isLoading = true
                                coroutineScope.launch {
                                    when (val result = firestoreService.saveDeferralRequest(
                                        examDate = examDate,
                                        requestedTime = requestedTime,
                                        reason = reason
                                    )) {
                                        is FirestoreResult.Success -> {
                                            isLoading = false
                                            Toast.makeText(
                                                context,
                                                "Request submitted successfully!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            navController.navigate("RequestSent") {
                                                popUpTo("ExamDeferralForm") { inclusive = true }
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
                            modifier = Modifier.size(40.dp)
                        )
                    }
                )
            }
        }
    }
}