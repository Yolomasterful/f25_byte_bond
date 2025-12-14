package com.example.myapplication.ui.theme.studentDashb.deferralRequests

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StatusViewerScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val firestoreService = remember { FirestoreService() }

    var requests by remember { mutableStateOf<List<DeferralRequest>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Load requests on screen open
    LaunchedEffect(Unit) {
        isLoading = true
        when (val result = firestoreService.getUserRequests()) {
            is FirestoreResult.Success -> {
                requests = result.data
                isLoading = false
            }
            is FirestoreResult.Error -> {
                errorMessage = result.message
                isLoading = false
            }
        }
    }

    // Refresh function
    fun refreshRequests() {
        coroutineScope.launch {
            isLoading = true
            when (val result = firestoreService.getUserRequests()) {
                is FirestoreResult.Success -> {
                    requests = result.data
                    isLoading = false
                }
                is FirestoreResult.Error -> {
                    errorMessage = result.message
                    isLoading = false
                }
            }
        }
    }

    MainLayout(
        navController = navController,
        pageName = "Deferral Requests"
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header with refresh button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Requests (${requests.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                TextButton(onClick = { refreshRequests() }) {
                    Text("Refresh")
                }
            }

            // Loading state
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            // Error state
            else if (errorMessage != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error: $errorMessage",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { refreshRequests() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            // Empty state
            else if (requests.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No deferral requests yet",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Submit a request to see it here",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            // List of requests
            else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(requests) { request ->
                        DeferralRequestCard(
                            request = request,
                            onClick = {
                                navController.navigate("ExamDetailScreen/${request.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeferralRequestCard(
    request: DeferralRequest,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Request #${request.id.take(8)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                StatusBadge(status = request.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Exam Date",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = request.examDate,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Requested Time",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = request.requestedTime,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Submitted",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatDate(request.submittedAt),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Reason (shortened)
            Text(
                text = "Reason:",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = if (request.reason.length > 80) {
                    "${request.reason.take(80)}..."
                } else {
                    request.reason
                },
                style = MaterialTheme.typography.bodySmall
            )

            // Review notes if available
            if (!request.reviewNotes.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
//                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Review Notes:",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = request.reviewNotes,
                    style = MaterialTheme.typography.bodySmall,
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

@Composable
fun StatusBadge(status: String) {
    val (backgroundColor, textColor, statusText) = when (status) {
        "PENDING" -> Triple(Color(0xFFFFF9C4), Color(0xFFF57F17), "Pending")
        "APPROVED" -> Triple(Color(0xFFC8E6C9), Color(0xFF2E7D32), "Approved")
        "DENIED" -> Triple(Color(0xFFFFCDD2), Color(0xFFC62828), "Denied")
        else -> Triple(Color.LightGray, Color.DarkGray, "Unknown")
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = statusText,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}