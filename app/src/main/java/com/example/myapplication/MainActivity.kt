package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.Image
import com.example.myapplication.ui.theme.LoginScreen
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface




private val navigation: Any = TODO()

//Class structure of the app
enum class AppScreen {
    Login,
//    TypeUser,
//    Student,
//    Proffessors,
//    Proctor,
//    Admin
}

// MainActivity function responsible for running the main program
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContent {
            MaterialTheme {
                Surface {
                    ExamDeferApp()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
// The following function has the main top bar before the user logs in
@Composable
fun ExamDeferAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        // Title of the navigation bar
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        // Icon for the top bar
        navigationIcon = {
            Image(
                painter = painterResource(id = R.mipmap.macewan_logo),
                contentDescription = "Macewan Logo",
                modifier = Modifier
            )

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Function for the skeleton of the app
fun ExamDeferApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            ExamDeferAppBar()
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = AppScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.Login.name) {
                LoginScreen(navController)
            }
        }
    }
}}


