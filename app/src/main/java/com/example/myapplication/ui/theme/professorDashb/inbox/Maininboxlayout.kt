package com.example.myapplication.ui.theme.professorDashb.inbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import com.example.myapplication.ui.theme.professorDashb.inbox.data.Message
//import com.example.myapplication.ui.theme.professorDashb.inbox.data.MessagingRepository
import com.example.myapplication.ui.theme.whiteBox

@Composable
fun MessageRequestsTabs() {
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
                            if (isSelected) MaterialTheme.colorScheme.onSurface.copy(alpha=0.08f)
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
            1 -> RequestsTab()
        }
    }}
}

@Composable
fun MessagesTab() {

    Column(modifier = Modifier.padding(16.dp)) {
    whiteBox{
        Text("Message #1")
    }

    }
}

@Composable
fun RequestsTab() {
    Column(modifier = Modifier.padding(16.dp)) {
        whiteBox{
            Text("Request #1")
        }

    }
}




