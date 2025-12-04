package com.example.myapplication.ui.theme

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
        val userId = auth.currentUser?.uid ?: return

        listener = firestore.collection("deferralRequests")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshots, error ->
                if (error != null) return@addSnapshotListener

                snapshots?.documentChanges?.forEach { change ->
                    if (change.type == com.google.firebase.firestore.DocumentChange.Type.MODIFIED) {
                        val doc = change.document
                        val status = doc.getString("status") ?: return@forEach
                        val reviewedAt = doc.getLong("reviewedAt")

                        // Only notify if recently reviewed (within last 10 seconds)
                        if (reviewedAt != null && System.currentTimeMillis() - reviewedAt < 10000) {
                            when (status) {
                                "APPROVED" -> showNotification(
                                    "Request Approved ✓",
                                    "Your deferral request for ${doc.getString("examDate")} has been approved!",
                                    doc.id
                                )
                                "DENIED" -> showNotification(
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
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with your app icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID_BASE + requestId.hashCode(), notification)
    }
}