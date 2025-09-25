package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.TextField


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)


        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android App",
                        modifier = Modifier.padding(innerPadding)
                    )
                    LoginScreen()
                }
            }
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name",
        modifier = modifier
    )
}

@Composable
fun LoginScreen() {
    val email = remember { mutableStateOf("") } 

    Column {
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            placeholder = { Text("Email") }
        )

        Button(onClick = { /* login */ }) {
            Text("Login")
        }
    }
}
