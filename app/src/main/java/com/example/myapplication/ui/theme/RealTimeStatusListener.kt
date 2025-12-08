package com.example.myapplication.logic.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class RealTimeStatusListener(private val context: Context) {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var listener: ListenerRegistration? = null
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        private const val CHANNEL_ID = "deferral_status_channel"
        private const val CHANNEL_NAME = "Deferral Status Updates"
        private const val NOTIFICATION_ID_BASE = 1000
    }

    init {
        createNotificationChannel()
    }

    fun startListening() {
        val userId = auth.currentUser?.uid ?: run {
            android.util.Log.e("NotificationDebug", "No user logged in")
            return
        }

        android.util.Log.d("NotificationDebug", "Starting listener for user: $userId")

        listener = firestore.collection("deferralRequests")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    android.util.Log.e("NotificationDebug", "Listener error: ${error.message}")
                    return@addSnapshotListener
                }

                android.util.Log.d("NotificationDebug", "Snapshot received, changes: ${snapshots?.documentChanges?.size}")

                snapshots?.documentChanges?.forEach { change ->
                    android.util.Log.d("NotificationDebug", "Change type: ${change.type}")

                    if (change.type == com.google.firebase.firestore.DocumentChange.Type.MODIFIED) {
                        val doc = change.document
                        val status = doc.getString("status") ?: return@forEach

                        android.util.Log.d("NotificationDebug", "Status changed to: $status for doc: ${doc.id}")

                        when (status) {
                            "APPROVED" -> {
                                android.util.Log.d("NotificationDebug", "Showing APPROVED notification")
                                showNotification(
                                    "Request Approved ✓",
                                    "Your deferral request for ${doc.getString("examDate")} has been approved!",
                                    doc.id
                                )
                            }
                            "DENIED" -> {
                                android.util.Log.d("NotificationDebug", "Showing DENIED notification")
                                showNotification(
                                    "Request Denied",
                                    "Your deferral request for ${doc.getString("examDate")} has been denied.",
                                    doc.id
                                )
                            }
                        }
                    }
                }
            }
    }

    fun stopListening() {
        listener?.remove()
        listener = null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for deferral request status changes"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(title: String, message: String, requestId: String) {
        android.util.Log.d("NotificationDebug", "showNotification called: $title")

        try {
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            val notifId = NOTIFICATION_ID_BASE + requestId.hashCode()
            android.util.Log.d("NotificationDebug", "Showing notification with ID: $notifId")
            notificationManager.notify(notifId, notification)
            android.util.Log.d("NotificationDebug", "Notification shown successfully")
        } catch (e: Exception) {
            android.util.Log.e("NotificationDebug", "Error showing notification: ${e.message}")
        }
    }
}