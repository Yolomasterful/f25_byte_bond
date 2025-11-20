/*
------------------------------------------------------------
 File: ExamDeferralForm.kt
 Module: studentDashb/RequestDeferral

 PURPOSE:
    Screen where a student fills in the deferral request form.
    Collects reason, preferred exam date, and preferred time.
    Validates inputs and submits the request through ViewModel.

 REQUIRED UI:
    - TextField: Reason for deferral
    - Date Picker: Preferred exam date
    - Time Picker: Preferred exam time
    - Submit Button
    - Loading indicator (during submit)
    - Error message text (if submission fails)

 FUNCTIONS CALLED (from StudentViewModel):
    - updateReason(reason: String)
    - updatePreferredDate(date: String)
    - updatePreferredTime(time: String)
    - submitDeferralForm(onSuccess: () -> Unit, onError: (String) -> Unit)

 NAVIGATION:
    On success → navigate to RequestSent.kt
------------------------------------------------------------
*/

package com.example.myapplication.ui.theme.studentDashb.RequestDeferral

