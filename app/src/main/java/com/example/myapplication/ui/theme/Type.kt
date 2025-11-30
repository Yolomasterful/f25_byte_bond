package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
//    uTypetext = TextStyle(
//
//            fontSize = 28.sp,
//        fontWeight = FontWeight(400),
//        color = Color.Black
//)
)


val Typography.uTypetext: TextStyle
    @Composable
    get() = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight(400),
            color = MaterialTheme.colorScheme.onPrimary
        )

val Typography.uTitletext: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight(600),
        color = MaterialTheme.colorScheme.onPrimary
    )



    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */


@Composable
fun uSubTitle(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp),
        fontSize = 32.sp,
        textAlign = TextAlign.Center

    )
}

