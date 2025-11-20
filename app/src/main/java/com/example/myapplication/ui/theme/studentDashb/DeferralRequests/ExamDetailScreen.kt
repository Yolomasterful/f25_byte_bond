/*
------------------------------------------------------------
 File: ExamDetailScreen.kt
 Module: studentDashb/DeferralRequests

 PURPOSE:
    Shows full details for a single deferral request selected by the student.
    Displays approval status, denial reasons, or final exam schedule.

 REQUIRED UI:
    - Reason (full text)
    - Preferred date/time
    - Status badge: Pending / Approved / Denied
    - If Denied: show instructor's denialReason
    - If Approved: show finalDate
    - Back button → returns to StatusViewerScreen

 FUNCTIONS CALLED (from StudentViewModel):
    - selectedRequest: StateFlow<DeferralRequest?>
      (Screen must observe this state)

 NAVIGATION:
    Back → StatusViewerScreen.kt
------------------------------------------------------------
*/

package com.example.myapplication.ui.theme.studentDashb.DeferralRequests

