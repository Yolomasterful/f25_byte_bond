
package com.example.myapplication
import com.example.myapplication.ui.theme.SelectUserType
import com.example.myapplication.ui.theme.MyApplicationTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.Image
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.ui.theme.AdminDashb.ADashboard
import com.example.myapplication.ui.theme.AdminDashb.AdminLogin
import com.example.myapplication.ui.theme.ProctorDashb.PDashboard
import com.example.myapplication.ui.theme.studentDashb.StudentLogin
import com.example.myapplication.ui.theme.studentDashb.SDashboard
import com.example.myapplication.ui.theme.professorDashb.PfDashboard
import com.example.myapplication.ui.theme.professorDashb.ProfessorLogin
import com.example.myapplication.ui.theme.ProctorDashb.ProctorLogin



//Class structure of the app
enum class AppScreen {
    TypeUser,
}

// MainActivity function responsible for running the main program
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContent {
            MyApplicationTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExamDeferApp()
                }
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
        title = { Text(stringResource(id = R.string.app_name), color = MaterialTheme.colorScheme.onPrimary) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
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
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Scaffold(
//        topBar = {
//            if ((currentRoute != AppScreen.Login.name) && (currentRoute != AppScreen.TypeUser.name)) {
//                ExamDeferAppBar()
//            }
//        }
    )
    { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = AppScreen.TypeUser.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.TypeUser.name) {
                SelectUserType(navController)
            }
            composable("login_student") {
                StudentLogin(navController)
            }
            composable("student_dashboard") {
                SDashboard(navController)
            }

            composable("login_professor") {
                ProfessorLogin(navController)
            }

            composable("professor_dashboard") {
                PfDashboard(navController)
            }

            composable("login_proctor") {
                ProctorLogin(navController)
            }

            composable("proctor_dashboard") {
                PDashboard(navController)
            }

            composable("login_admin") {
                AdminLogin(navController)
            }

            composable("admin_dashboard") {
                ADashboard(navController)
            }


        }
    }
}


