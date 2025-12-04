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
}