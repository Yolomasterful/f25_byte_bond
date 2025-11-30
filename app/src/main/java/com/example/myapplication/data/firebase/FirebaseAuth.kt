package com.example.myapplication.data.firebase

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class FirebaseAuthService {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): AuthResult {

        // Email validation
        if (!email.endsWith("@macewan.ca") && !email.endsWith("@mymacewan.ca")) {
            return AuthResult.Error("Email must end with @macewan.ca or @mymacewan.ca")
        }

        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Unknown error occurred")
        }
    }
}
