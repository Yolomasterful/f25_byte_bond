package com.example.myapplication.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await



data class DeferralRequest(
    val id: String = "",
    val userId: String = "",
    val studentEmail: String = "",
    val examDate: String = "",
    val requestedTime: String = "",
    val reason: String = "",
    val status: String = "PENDING", // PENDING, APPROVED, DENIED
    val submittedAt: Long = System.currentTimeMillis(),
    val reviewedAt: Long? = null,
    val reviewedBy: String? = null,
    val reviewNotes: String? = null
)
// Add this data class after DeferralRequest
data class ExamRequest(
    val id: String = "",
    val professorId: String = "",
    val professorEmail: String = "",
    val roomPreference: String = "",
    val timeAllowed: String = "",
    val materialsNeeded: String = "",
    val notes: String = "",
    val status: String = "PENDING", // PENDING, APPROVED, REJECTED
    val submittedAt: Long = System.currentTimeMillis(),
    val reviewedAt: Long? = null,
    val reviewedBy: String? = null
)

// Add this data class for inbox messages
data class InboxMessage(
    val id: String = "",
    val recipientId: String = "", // Admin user ID
    val recipientRole: String = "ADMIN", // ADMIN, PROFESSOR, PROCTOR
    val senderId: String = "",
    val senderEmail: String = "",
    val senderRole: String = "PROFESSOR",
    val type: String = "EXAM_REQUEST", // EXAM_REQUEST, DEFERRAL_REQUEST, GENERAL
    val title: String = "",
    val message: String = "",
    val relatedDocumentId: String = "", // ID of the exam request or deferral
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
sealed class FirestoreResult<out T> {
    data class Success<T>(val data: T) : FirestoreResult<T>()
    data class Error(val message: String) : FirestoreResult<Nothing>()
}

class FirestoreService {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val requestsCollection = firestore.collection("deferralRequests")

    /**
     * Save a new deferral request
     */
    suspend fun saveDeferralRequest(
        examDate: String,
        requestedTime: String,
        reason: String
    ): FirestoreResult<String> {
        return try {
            val currentUser = auth.currentUser
                ?: return FirestoreResult.Error("User not logged in")

            // Create request document
            val requestRef = requestsCollection.document()
            val requestId = requestRef.id

            val request = hashMapOf(
                "id" to requestId,
                "userId" to currentUser.uid,
                "studentEmail" to (currentUser.email ?: ""),
                "examDate" to examDate,
                "requestedTime" to requestedTime,
                "reason" to reason,
                "status" to "PENDING",
                "submittedAt" to System.currentTimeMillis(),
                "reviewedAt" to null,
                "reviewedBy" to null,
                "reviewNotes" to null
            )

            requestRef.set(request).await()

            FirestoreResult.Success(requestId)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to save request")
        }
    }

    /**
     * Get all requests for current user
     */
    suspend fun getUserRequests(): FirestoreResult<List<DeferralRequest>> {
        return try {
            val currentUser = auth.currentUser
                ?: return FirestoreResult.Error("User not logged in")

            val snapshot = requestsCollection
                .whereEqualTo("userId", currentUser.uid)
                .orderBy("submittedAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val requests = snapshot.documents.mapNotNull { doc ->
                DeferralRequest(
                    id = doc.getString("id") ?: "",
                    userId = doc.getString("userId") ?: "",
                    studentEmail = doc.getString("studentEmail") ?: "",
                    examDate = doc.getString("examDate") ?: "",
                    requestedTime = doc.getString("requestedTime") ?: "",
                    reason = doc.getString("reason") ?: "",
                    status = doc.getString("status") ?: "PENDING",
                    submittedAt = doc.getLong("submittedAt") ?: 0L,
                    reviewedAt = doc.getLong("reviewedAt"),
                    reviewedBy = doc.getString("reviewedBy"),
                    reviewNotes = doc.getString("reviewNotes")
                )
            }

            FirestoreResult.Success(requests)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to load requests")
        }
    }

    /**
     * Get a specific request by ID
     */
    suspend fun getRequestById(requestId: String): FirestoreResult<DeferralRequest?> {
        return try {
            val doc = requestsCollection.document(requestId).get().await()

            if (!doc.exists()) {
                return FirestoreResult.Success(null)
            }

            val request = DeferralRequest(
                id = doc.getString("id") ?: "",
                userId = doc.getString("userId") ?: "",
                studentEmail = doc.getString("studentEmail") ?: "",
                examDate = doc.getString("examDate") ?: "",
                requestedTime = doc.getString("requestedTime") ?: "",
                reason = doc.getString("reason") ?: "",
                status = doc.getString("status") ?: "PENDING",
                submittedAt = doc.getLong("submittedAt") ?: 0L,
                reviewedAt = doc.getLong("reviewedAt"),
                reviewedBy = doc.getString("reviewedBy"),
                reviewNotes = doc.getString("reviewNotes")
            )

            FirestoreResult.Success(request)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to load request")
        }
    }

    /**
     * Update request status (for professors/admins)
     */
    suspend fun updateRequestStatus(
        requestId: String,
        status: String,
        reviewedBy: String,
        reviewNotes: String
    ): FirestoreResult<Boolean> {
        return try {
            val updates = hashMapOf(
                "status" to status,
                "reviewedAt" to System.currentTimeMillis(),
                "reviewedBy" to reviewedBy,
                "reviewNotes" to reviewNotes
            )

            requestsCollection.document(requestId).update(updates as Map<String, Any>).await()

            FirestoreResult.Success(true)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to update request")
        }
    }

    /**
     * Get all pending requests (for professors/admins)
     */
    suspend fun getAllPendingRequests(): FirestoreResult<List<DeferralRequest>> {
        return try {
            val snapshot = requestsCollection
                .whereEqualTo("status", "PENDING")
                .orderBy("submittedAt", Query.Direction.ASCENDING)
                .get()
                .await()

            val requests = snapshot.documents.mapNotNull { doc ->
                DeferralRequest(
                    id = doc.getString("id") ?: "",
                    userId = doc.getString("userId") ?: "",
                    studentEmail = doc.getString("studentEmail") ?: "",
                    examDate = doc.getString("examDate") ?: "",
                    requestedTime = doc.getString("requestedTime") ?: "",
                    reason = doc.getString("reason") ?: "",
                    status = doc.getString("status") ?: "PENDING",
                    submittedAt = doc.getLong("submittedAt") ?: 0L,
                    reviewedAt = doc.getLong("reviewedAt"),
                    reviewedBy = doc.getString("reviewedBy"),
                    reviewNotes = doc.getString("reviewNotes")
                )
            }

            FirestoreResult.Success(requests)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to load requests")
        }
    }
    suspend fun getApprovedRequests(): FirestoreResult<List<DeferralRequest>> {
        return try {
            val snapshot = requestsCollection
                .whereEqualTo("status", "APPROVED")
                .orderBy("reviewedAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val requests = snapshot.documents.mapNotNull { doc ->
                DeferralRequest(
                    id = doc.getString("id") ?: "",
                    userId = doc.getString("userId") ?: "",
                    studentEmail = doc.getString("studentEmail") ?: "",
                    examDate = doc.getString("examDate") ?: "",
                    requestedTime = doc.getString("requestedTime") ?: "",
                    reason = doc.getString("reason") ?: "",
                    status = doc.getString("status") ?: "APPROVED",
                    submittedAt = doc.getLong("submittedAt") ?: 0L,
                    reviewedAt = doc.getLong("reviewedAt"),
                    reviewedBy = doc.getString("reviewedBy"),
                    reviewNotes = doc.getString("reviewNotes")
                )
            }

            FirestoreResult.Success(requests)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to load approved requests")
        }
    }
    private val examRequestsCollection = firestore.collection("examRequests")
    private val inboxCollection = firestore.collection("inbox")

    /**
     * Save a new exam request from professor
     */
    suspend fun saveExamRequest(
        roomPreference: String,
        timeAllowed: String,
        materialsNeeded: String,
        notes: String
    ): FirestoreResult<String> {
        return try {
            val currentUser = auth.currentUser
                ?: return FirestoreResult.Error("User not logged in")

            // Create exam request document
            val requestRef = examRequestsCollection.document()
            val requestId = requestRef.id

            val request = hashMapOf(
                "id" to requestId,
                "professorId" to currentUser.uid,
                "professorEmail" to (currentUser.email ?: ""),
                "roomPreference" to roomPreference,
                "timeAllowed" to timeAllowed,
                "materialsNeeded" to materialsNeeded,
                "notes" to notes,
                "status" to "PENDING",
                "submittedAt" to System.currentTimeMillis(),
                "reviewedAt" to null,
                "reviewedBy" to null
            )

            requestRef.set(request).await()

            // Create inbox notification for all admins
            createInboxNotificationForAdmins(
                senderId = currentUser.uid,
                senderEmail = currentUser.email ?: "",
                requestId = requestId,
                roomPreference = roomPreference
            )

            FirestoreResult.Success(requestId)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to save exam request")
        }
    }

    /**
     * Create inbox notifications for all admins
     */
    private suspend fun createInboxNotificationForAdmins(
        senderId: String,
        senderEmail: String,
        requestId: String,
        roomPreference: String
    ) {
        try {
            // Get all admin users
            val adminSnapshot = firestore.collection("users")
                .whereEqualTo("role", "ADMIN")
                .get()
                .await()

            // If no users collection exists, create a notification for a default admin
            if (adminSnapshot.isEmpty) {
                // Create notification for default admin inbox
                val messageRef = inboxCollection.document()
                val messageId = messageRef.id

                val message = hashMapOf(
                    "id" to messageId,
                    "recipientId" to "ADMIN_DEFAULT", // Placeholder
                    "recipientRole" to "ADMIN",
                    "senderId" to senderId,
                    "senderEmail" to senderEmail,
                    "senderRole" to "PROFESSOR",
                    "type" to "EXAM_REQUEST",
                    "title" to "New Exam Booking Request",
                    "message" to "Professor $senderEmail has submitted a new exam booking request for room: $roomPreference",
                    "relatedDocumentId" to requestId,
                    "isRead" to false,
                    "createdAt" to System.currentTimeMillis()
                )

                messageRef.set(message).await()
            } else {
                // Create notification for each admin
                adminSnapshot.documents.forEach { adminDoc ->
                    val adminId = adminDoc.id

                    val messageRef = inboxCollection.document()
                    val messageId = messageRef.id

                    val message = hashMapOf(
                        "id" to messageId,
                        "recipientId" to adminId,
                        "recipientRole" to "ADMIN",
                        "senderId" to senderId,
                        "senderEmail" to senderEmail,
                        "senderRole" to "PROFESSOR",
                        "type" to "EXAM_REQUEST",
                        "title" to "New Exam Booking Request",
                        "message" to "Professor $senderEmail has submitted a new exam booking request for room: $roomPreference",
                        "relatedDocumentId" to requestId,
                        "isRead" to false,
                        "createdAt" to System.currentTimeMillis()
                    )

                    messageRef.set(message).await()
                }
            }
        } catch (e: Exception) {
            // Log error but don't fail the main operation
            android.util.Log.e("FirestoreService", "Failed to create inbox notifications", e)
        }
    }

    /**
     * Get all exam requests for current professor
     */
    suspend fun getProfessorExamRequests(): FirestoreResult<List<ExamRequest>> {
        return try {
            val currentUser = auth.currentUser
                ?: return FirestoreResult.Error("User not logged in")

            val snapshot = examRequestsCollection
                .whereEqualTo("professorId", currentUser.uid)
                .orderBy("submittedAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val requests = snapshot.documents.mapNotNull { doc ->
                ExamRequest(
                    id = doc.getString("id") ?: "",
                    professorId = doc.getString("professorId") ?: "",
                    professorEmail = doc.getString("professorEmail") ?: "",
                    roomPreference = doc.getString("roomPreference") ?: "",
                    timeAllowed = doc.getString("timeAllowed") ?: "",
                    materialsNeeded = doc.getString("materialsNeeded") ?: "",
                    notes = doc.getString("notes") ?: "",
                    status = doc.getString("status") ?: "PENDING",
                    submittedAt = doc.getLong("submittedAt") ?: 0L,
                    reviewedAt = doc.getLong("reviewedAt"),
                    reviewedBy = doc.getString("reviewedBy")
                )
            }

            FirestoreResult.Success(requests)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to load exam requests")
        }
    }

    /**
     * Get all pending exam requests (for admins)
     */
    suspend fun getAllPendingExamRequests(): FirestoreResult<List<ExamRequest>> {
        return try {
            val snapshot = examRequestsCollection
                .whereEqualTo("status", "PENDING")
                .orderBy("submittedAt", Query.Direction.ASCENDING)
                .get()
                .await()

            val requests = snapshot.documents.mapNotNull { doc ->
                ExamRequest(
                    id = doc.getString("id") ?: "",
                    professorId = doc.getString("professorId") ?: "",
                    professorEmail = doc.getString("professorEmail") ?: "",
                    roomPreference = doc.getString("roomPreference") ?: "",
                    timeAllowed = doc.getString("timeAllowed") ?: "",
                    materialsNeeded = doc.getString("materialsNeeded") ?: "",
                    notes = doc.getString("notes") ?: "",
                    status = doc.getString("status") ?: "PENDING",
                    submittedAt = doc.getLong("submittedAt") ?: 0L,
                    reviewedAt = doc.getLong("reviewedAt"),
                    reviewedBy = doc.getString("reviewedBy")
                )
            }

            FirestoreResult.Success(requests)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to load exam requests")
        }
    }

    /**
     * Get inbox messages for current user
     */
    suspend fun getInboxMessages(): FirestoreResult<List<InboxMessage>> {
        return try {
            val currentUser = auth.currentUser
                ?: return FirestoreResult.Error("User not logged in")

            val snapshot = inboxCollection
                .whereEqualTo("recipientId", currentUser.uid)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val messages = snapshot.documents.mapNotNull { doc ->
                InboxMessage(
                    id = doc.getString("id") ?: "",
                    recipientId = doc.getString("recipientId") ?: "",
                    recipientRole = doc.getString("recipientRole") ?: "",
                    senderId = doc.getString("senderId") ?: "",
                    senderEmail = doc.getString("senderEmail") ?: "",
                    senderRole = doc.getString("senderRole") ?: "",
                    type = doc.getString("type") ?: "",
                    title = doc.getString("title") ?: "",
                    message = doc.getString("message") ?: "",
                    relatedDocumentId = doc.getString("relatedDocumentId") ?: "",
                    isRead = doc.getBoolean("isRead") ?: false,
                    createdAt = doc.getLong("createdAt") ?: 0L
                )
            }

            FirestoreResult.Success(messages)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to load inbox messages")
        }
    }

    /**
     * Get inbox messages for admins (uses default ID if user collection doesn't exist)
     */
    suspend fun getAdminInboxMessages(): FirestoreResult<List<InboxMessage>> {
        return try {
            val currentUser = auth.currentUser
                ?: return FirestoreResult.Error("User not logged in")

            // Try to get messages for specific admin OR default admin
            val snapshot = inboxCollection
                .whereIn("recipientId", listOf(currentUser.uid, "ADMIN_DEFAULT"))
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val messages = snapshot.documents.mapNotNull { doc ->
                InboxMessage(
                    id = doc.getString("id") ?: "",
                    recipientId = doc.getString("recipientId") ?: "",
                    recipientRole = doc.getString("recipientRole") ?: "",
                    senderId = doc.getString("senderId") ?: "",
                    senderEmail = doc.getString("senderEmail") ?: "",
                    senderRole = doc.getString("senderRole") ?: "",
                    type = doc.getString("type") ?: "",
                    title = doc.getString("title") ?: "",
                    message = doc.getString("message") ?: "",
                    relatedDocumentId = doc.getString("relatedDocumentId") ?: "",
                    isRead = doc.getBoolean("isRead") ?: false,
                    createdAt = doc.getLong("createdAt") ?: 0L
                )
            }

            FirestoreResult.Success(messages)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to load inbox messages")
        }
    }

    /**
     * Mark message as read
     */
    suspend fun markMessageAsRead(messageId: String): FirestoreResult<Boolean> {
        return try {
            inboxCollection.document(messageId)
                .update("isRead", true)
                .await()

            FirestoreResult.Success(true)
        } catch (e: Exception) {
            FirestoreResult.Error(e.message ?: "Failed to mark message as read")
        }
    }
}
