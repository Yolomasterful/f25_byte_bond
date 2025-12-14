package com.example.myapplication.ui.theme.adminDashb.bookARoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.AppwhiteTextField
import com.example.myapplication.ui.theme.MainLayout
import com.example.myapplication.ui.theme.SearchDropdown
import com.example.myapplication.ui.theme.basicButton

@Composable
fun BookRoom(navController: NavHostController) {
    MainLayout(
        navController = navController,
        pageName = "Book a Room"

    ) {

        Column(
            modifier = Modifier
//                .fillMaxSize()
                .verticalScroll(rememberScrollState())
//                .padding(bottom = 40.dp)
//                .align(Alignment.CenterHorizontally),
        ) {
            val context = LocalContext.current
            var capacity by remember { mutableStateOf("") }
            var roomNum by remember { mutableStateOf("") }


            Spacer(modifier = Modifier.height(40.dp))
            Text(
                "Exam Date",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))
            SearchDropdown(
                //Add data retrieved infor here of outside this function?
                options = listOf(
                    "Calender goes here!"
                )
            ) {
                // add the code to retrieve?
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Requested Time",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))
            SearchDropdown(
                //Add data retrieved infor here of outside this function?
                options = listOf(
                    "08:00 am",
                    "08:30 am"
                )
            ) {
                // add the code to retrieve?
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Room preference:",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))
            AppwhiteTextField(
                // The email box details
                value = roomNum,
                onValueChange = { roomNum = it },
                placeholderText = "",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Capacity:",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))

            AppwhiteTextField(
                // The email box details
                value = capacity,
                onValueChange = { capacity = it },
                placeholderText = "",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(20.dp))

            basicButton(
                onClick = { navController.navigate("RoomBooked") },
                imageContent = {
                    Image(
                        painter = painterResource(id = R.drawable.send_button),
                        contentDescription = "Send Button",
                        modifier = Modifier
                            .size(40.dp),
                    )
                }
            )


        }
    }
}