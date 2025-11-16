package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun MainLayout(
    navController: NavHostController,
    pageName: String = "",

    content: @Composable () -> Unit
) {
    Scaffold(
        content = { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    "$pageName",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.uTitletext,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(60.dp))
                backArrow(
                    navController = navController,
                    modifier = Modifier
                )
                content()

            }

        }
    )
}