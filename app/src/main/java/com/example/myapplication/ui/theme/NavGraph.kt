package com.example.myapplication.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Your UI screens

// Dashboards with correct package names
import com.example.myapplication.ui.theme.studentDashb.SDashboard
import com.example.myapplication.ui.theme.professorDashb.PfDashboard
import com.example.myapplication.ui.theme.proctorDashb.PDashboard
import com.example.myapplication.ui.theme.adminDashb.ADashboard
import com.example.myapplication.ui.theme.adminDashb.bookARoom.BookRoom
import com.example.myapplication.ui.theme.adminDashb.bookARoom.RoomBooked
import com.example.myapplication.ui.theme.adminDashb.inbox.AdminInbox
import com.example.myapplication.ui.theme.proctorDashb.availability.SetAvailability
import com.example.myapplication.ui.theme.proctorDashb.inbox.InboxPage

// Additional screens
import com.example.myapplication.ui.theme.professorDashb.inbox.Messages
import com.example.myapplication.ui.theme.professorDashb.bookExams.ExamReqForm
import com.example.myapplication.ui.theme.professorDashb.bookExams.ExamReqSent
import com.example.myapplication.ui.theme.professorDashb.bookExams.UploadExam
import com.example.myapplication.ui.theme.studentDashb.deferralRequests.StatusViewerScreen
import com.example.myapplication.ui.theme.studentDashb.deferralRequests.ExamDetailScreen
import com.example.myapplication.ui.theme.studentDashb.requestDeferral.ExamDeferralForm
import com.example.myapplication.ui.theme.studentDashb.requestDeferral.ExamDeferralForm2
import com.example.myapplication.ui.theme.studentDashb.requestDeferral.RequestSent

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "select_user",
        modifier = modifier
    ) {

        /** ───────────────────────────────
         *  1. User Type Selection Screen
         *  ─────────────────────────────── */
        composable("select_user") {
            SelectUserType(navController)
        }

        /** ───────────────────────────────
         *  2. Unified Login Screen
         *  ─────────────────────────────── */
        composable("login/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "student"

            LoginScreen(
                navController = navController,
                userType = userType
            )
        }

        /** ───────────────────────────────
         *  3. Student Screens
         *  ─────────────────────────────── */
        composable("dashboard_student") {
            SDashboard(navController)
        }

        composable("StatusViewerScreen") {
            StatusViewerScreen(navController)
        }

        composable("ExamDetailScreen/{requestId}") { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId")
            ExamDetailScreen(navController, requestId)
        }

        composable("ExamDeferralForm") {
            ExamDeferralForm(navController)
        }

        composable("ExamDeferralForm2") {
            ExamDeferralForm2(navController)
        }

        composable("RequestSent") {
            RequestSent(navController)
        }

        /** ───────────────────────────────
         *  4. Professor Screens
         *  ─────────────────────────────── */
        composable("dashboard_professor") {
            PfDashboard(navController)
        }

        composable("Messages") {
            Messages(navController)
        }

        composable("ExamReqForm") {
            ExamReqForm(navController)
        }

        composable("ExamReqSent") {
            ExamReqSent(navController)
        }

        composable("UploadExam") {
            UploadExam(navController)
        }

        // Professor views deferral request details (reusing student's ExamDetailScreen)
        composable("ProfessorRequestDetail/{requestId}") { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId")
            ExamDetailScreen(navController, requestId)
        }

        /** ───────────────────────────────
         *  5. Proctor Screen
         *  ─────────────────────────────── */
        composable("dashboard_proctor") {
            PDashboard(navController)
        }

        composable("InboxPage") {
            InboxPage(navController)
        }

        composable("SetAvailability") {
            SetAvailability(navController)
        }

        composable("AdminInbox") {
            AdminInbox(navController)
        }

        /** ───────────────────────────────
         *  6. Admin Screen
         *  ─────────────────────────────── */
        composable("dashboard_admin") {
            ADashboard(navController)
        }

        composable("AdminInbox") {
            AdminInbox(navController)
        }

        composable("BookRoom") {
            BookRoom(navController)
        }

        composable("RoomBooked") {
            RoomBooked(navController)
        }

        // Admin views approved request details
        composable("AdminRequestDetail/{requestId}") { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId")
            ExamDetailScreen(navController, requestId)
        }
    }
}