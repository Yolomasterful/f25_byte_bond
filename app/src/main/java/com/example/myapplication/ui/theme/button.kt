package com.example.myapplication.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun backArrow(
    navController: NavHostController,
    modifier: Modifier = Modifier,

) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
    ) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .width(93.dp)
                .height(28.dp)
                .align(Alignment.TopStart),
            shape = RoundedCornerShape(size = 31.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, Color.Black)


        ) {
            Image(
                painter = painterResource(id = R.mipmap.backarrow),
                contentDescription = "ArrowBar",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}