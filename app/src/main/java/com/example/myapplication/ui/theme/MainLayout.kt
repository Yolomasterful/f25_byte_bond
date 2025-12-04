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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape


@Composable
fun MainLayout(
    navController: NavHostController,
    pageName: String = "",
    showBackArrow: Boolean = true,
    onBackClick: (() -> Unit)? = null,

    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        content = { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,


            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    "$pageName",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.uTitletext,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                if (showBackArrow) {
                    backArrow(
                        navController = navController,
                        onBackClick = onBackClick
                )}
                content()

            }

        }
    )
}


@Composable
fun whiteBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
