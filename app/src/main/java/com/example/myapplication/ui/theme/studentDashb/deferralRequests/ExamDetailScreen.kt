package com.example.myapplication.ui.theme.studentDashb.deferralRequests

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.firebase.DeferralRequest
import com.example.myapplication.data.firebase.FirestoreResult
import com.example.myapplication.data.firebase.FirestoreService
import com.example.myapplication.ui.theme.MainLayout
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ExamDetailScreen(
    navController: NavHostController,
    requestId: String? = null
) {
    val firestoreService = remember { FirestoreService() }

    var request by remember { mutableStateOf<DeferralRequest?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Load request on screen open
    LaunchedEffect(requestId) {
        if (requestId != null) {
            isLoading = true
            when (val result = firestoreService.getRequestById(requestId)) {
                is FirestoreResult.Success -> {
                    request = result.data
                    isLoading = false
                }
                is FirestoreResult.Error -> {
                    errorMessage = result.message
                    isLoading = false
                }
            }
        } else {
            errorMessage = "No request ID provided"
            isLoading = false
        }
    }

    MainLayout(
        navController = navController,
        pageName = "Request Details"
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error: $errorMessage",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { navController.popBackStack() }) {
                            Text("Go Back")
                        }
                    }
                }
            }
            request == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Request not found",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { navController.popBackStack() }) {
                            Text("Go Back")
                        }
                    }
                }
            }
            else -> {
                // Display request details
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Status Header
                    StatusHeaderCard(request!!)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Request Details
                    RequestDetailsCard(request!!)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Review Information (if reviewed)
                    if (request!!.reviewedAt != null) {
                        ReviewInformationCard(request!!)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Back Button
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back to List")
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusHeaderCard(request: DeferralRequest) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (request.status) {
                "PENDING" -> Color(0xFFFFF9C4)
                "APPROVED" -> Color(0xFFC8E6C9)
                "DENIED" -> Color(0xFFFFCDD2)
                else -> Color.LightGray
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (request.status) {
                    "PENDING" -> "⏳ Pending Review"
                    "APPROVED" -> "✓ Request Approved"
                    "DENIED" -> "✗ Request Denied"
                    else -> "Unknown Status"
                },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = when (request.status) {
                    "PENDING" -> Color(0xFFF57F17)
                    "APPROVED" -> Color(0xFF2E7D32)
                    "DENIED" -> Color(0xFFC62828)
                    else -> Color.DarkGray
                }
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Request ID: ${request.id.take(12)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Composable
private fun RequestDetailsCard(request: DeferralRequest) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Request Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            DetailRow(label = "Student Email", value = request.studentEmail)
            Spacer(modifier = Modifier.height(12.dp))

            DetailRow(label = "Exam Date", value = request.examDate)
            Spacer(modifier = Modifier.height(12.dp))

            DetailRow(label = "Requested Time", value = request.requestedTime)
            Spacer(modifier = Modifier.height(12.dp))

            DetailRow(
                label = "Submitted On",
                value = formatDateTime(request.submittedAt)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Reason for Deferral",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = request.reason,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}
@Composable
private fun ReviewInformationCard(request: DeferralRequest) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (request.status) {
                "APPROVED" -> Color(0xFFE8F5E9)
                "DENIED" -> Color(0xFFFFEBEE)
                else -> MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Review Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            request.reviewedBy?.let {
                DetailRow(label = "Reviewed By", value = it)
                Spacer(modifier = Modifier.height(12.dp))
            }

            request.reviewedAt?.let {
                DetailRow(label = "Reviewed On", value = formatDateTime(it))
                Spacer(modifier = Modifier.height(12.dp))
            }

            request.reviewNotes?.let { notes ->
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Review Notes",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Text(
                        text = notes,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(12.dp),
                        color = when (request.status) {
                            "APPROVED" -> Color(0xFF2E7D32)
                            "DENIED" -> Color(0xFFC62828)
                            else -> MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }
        }
    }
}
@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(140.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal
        )
    }
}
private fun formatDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
