// File: RequestsTab.kt (updated Maininboxlayout.kt)
package com.example.myapplication.ui.theme.professorDashb.inbox

import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.firebase.DeferralRequest
import com.example.myapplication.data.firebase.FirestoreResult
import com.example.myapplication.data.firebase.FirestoreService
import com.example.myapplication.ui.theme.whiteBox
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast

@Composable
fun MessageRequestsTabs(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Messages", "Requests")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(1.dp)
            )
    ) {
        Column {
            PrimaryTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                divider = {},
                indicator = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTab == index
                    Tab(
                        selected = isSelected,
                        onClick = { selectedTab = index },
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                                else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        text = {
                            Text(
                                text = title,
                                color = Color.Black,
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                0 -> MessagesTab()
                1 -> RequestsTab(navController)
            }
        }
    }
}

@Composable
fun MessagesTab() {
    Column(modifier = Modifier.padding(16.dp)) {
        whiteBox {
            Text("Message #1")
        }
    }
}

@Composable
fun RequestsTab(navController: NavHostController) {
    val firestoreService = remember { FirestoreService() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var requests by remember { mutableStateOf<List<DeferralRequest>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var updatingRequestId by remember { mutableStateOf<String?>(null) }

    fun loadRequests() {
        isLoading = true
        errorMessage = null
        scope.launch {
            when (val result = firestoreService.getAllPendingRequests()) {
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

    LaunchedEffect(Unit) {
        loadRequests()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error: $errorMessage",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { loadRequests() }) {
                        Text("Retry")
                    }
                }
            }
            requests.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No pending requests",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(requests) { request ->
                        RequestCard(
                            request = request,
                            isUpdating = updatingRequestId == request.id,
                            onAccept = {
                                updatingRequestId = request.id
                                scope.launch {
                                    val reviewerEmail = FirebaseAuth.getInstance().currentUser?.email ?: "unknown"
                                    when (val result = firestoreService.updateRequestStatus(
                                        requestId = request.id,
                                        status = "APPROVED",
                                        reviewedBy = reviewerEmail,
                                        reviewNotes = "Approved by professor"
                                    )) {
                                        is FirestoreResult.Success -> {
                                            requests = requests.filter { it.id != request.id }
                                            updatingRequestId = null
                                            Toast.makeText(context, "Request approved", Toast.LENGTH_SHORT).show()
                                        }
                                        is FirestoreResult.Error -> {
                                            updatingRequestId = null
                                            Toast.makeText(context, "Failed: ${result.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            },
                            onDeny = {
                                updatingRequestId = request.id
                                scope.launch {
                                    val reviewerEmail = FirebaseAuth.getInstance().currentUser?.email ?: "unknown"
                                    when (val result = firestoreService.updateRequestStatus(
                                        requestId = request.id,
                                        status = "DENIED",
                                        reviewedBy = reviewerEmail,
                                        reviewNotes = "Denied by professor"
                                    )) {
                                        is FirestoreResult.Success -> {
                                            requests = requests.filter { it.id != request.id }
                                            updatingRequestId = null
                                            Toast.makeText(context, "Request denied", Toast.LENGTH_SHORT).show()
                                        }
                                        is FirestoreResult.Error -> {
                                            updatingRequestId = null
                                            Toast.makeText(context, "Failed: ${result.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            },
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
fun RequestCard(
    request: DeferralRequest,
    isUpdating: Boolean,
    onAccept: () -> Unit,
    onDeny: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isUpdating, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID: ${request.id.take(8)}...",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                StatusBadge(status = request.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = request.studentEmail,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Exam: ${request.examDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
                Text(
                    text = "Requested: ${request.requestedTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Submitted: ${formatTimestamp(request.submittedAt)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = request.reason,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onAccept,
                    enabled = !isUpdating,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    if (isUpdating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Accept")
                    }
                }

                Button(
                    onClick = onDeny,
                    enabled = !isUpdating,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    )
                ) {
                    if (isUpdating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Deny")
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val backgroundColor = when (status) {
        "PENDING" -> Color(0xFFFFA726)
        "APPROVED" -> Color(0xFF66BB6A)
        "DENIED" -> Color(0xFFEF5350)
        else -> Color.Gray
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor.copy(alpha = 0.2f)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = backgroundColor,
            fontWeight = FontWeight.Bold
        )
    }
}

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}