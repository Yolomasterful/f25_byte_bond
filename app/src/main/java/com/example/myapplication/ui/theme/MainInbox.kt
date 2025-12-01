package com.example.myapplication.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MainInbox(
    modifier: Modifier = Modifier,
    navController: NavHostController
    ) {

        var query by remember { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }

        Column(modifier = Modifier.padding(8.dp)) {
            val onActiveChange: (Boolean) -> Unit = { expanded ->
                active = expanded
            }
            val colors1 = SearchBarDefaults.colors()


            OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = {
                        Text("Search")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(50),
                    singleLine = true,
                    leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.searchicon),
                                contentDescription = "downloadfile",
                                modifier = Modifier.size(24.dp)
                            )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
            )


        }


    }


