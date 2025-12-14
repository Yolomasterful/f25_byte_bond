package com.example.myapplication.ui.theme.adminDashb.inbox

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.firebase.DeferralRequest
import com.example.myapplication.data.firebase.FirestoreResult
import com.example.myapplication.data.firebase.FirestoreService
import com.example.myapplication.data.firebase.InboxMessage
import com.example.myapplication.ui.theme.MainLayout
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AdminInbox(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Inbox"
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        AdminInboxTabs(navController)
    }
}

@Composable
fun AdminInboxTabs(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Messages", "Approved Requests")

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
                0 -> AdminMessagesTab(navController)
                1 -> ApprovedRequestsTab(navController)
            }
        }
    }
}

@Composable
fun AdminMessagesTab(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val firestoreService = remember { FirestoreService() }

    var messages by remember { mutableStateOf<List<InboxMessage>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun loadMessages() {
        coroutineScope.launch {
            isLoading = true
            when (val result = firestoreService.getAdminInboxMessages()) {
                is FirestoreResult.Success -> {
                    messages = result.data
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
        loadMessages()
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { loadMessages() }) {
                        Text("Retry")
                    }
                }
            }
            messages.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No messages yet",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(messages) { message ->
                        InboxMessageCard(
                            message = message,
                            onClick = {
                                coroutineScope.launch {
                                    firestoreService.markMessageAsRead(message.id)
                                }
                                if (message.type == "EXAM_REQUEST") {
                                    navController.navigate("ExamRequestDetail/${message.relatedDocumentId}")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ApprovedRequestsTab(navController: NavHostController) {
    val firestoreService = remember { FirestoreService() }
    val scope = rememberCoroutineScope()

    var requests by remember { mutableStateOf<List<DeferralRequest>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun loadRequests() {
        isLoading = true
        errorMessage = null
        scope.launch {
            when (val result = firestoreService.getApprovedRequests()) {
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
                        text = "No approved requests",
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
                        ApprovedRequestCard(
                            request = request,
                            onClick = {
                                navController.navigate("AdminRequestDetail/${request.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InboxMessageCard(
    message: InboxMessage,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (message.isRead) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = message.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (message.isRead) FontWeight.Normal else FontWeight.Bold
                )

                if (!message.isRead) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Text(
                            text = "NEW",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "From: ${message.senderEmail}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message.message,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formatDateTime(message.createdAt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ApprovedRequestCard(
    request: DeferralRequest,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF66BB6A).copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "APPROVED",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF66BB6A),
                        fontWeight = FontWeight.Bold
                    )
                }
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
                    text = "Time: ${request.requestedTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Reviewed: ${formatTimestamp(request.reviewedAt ?: 0L)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Text(
                text = "Reviewed by: ${request.reviewedBy ?: "Unknown"}",
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
        }
    }
}

private fun formatDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun formatTimestamp(timestamp: Long): String {
    if (timestamp == 0L) return "N/A"
    val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}