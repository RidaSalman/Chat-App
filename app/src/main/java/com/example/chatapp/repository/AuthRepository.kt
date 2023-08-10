package com.example.chatapp.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    suspend fun signup(email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}