package com.example.chatapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().getReference("Users")

    suspend fun signup(username: String, email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        val firebaseUser = auth.currentUser
                        val userId = firebaseUser?.uid

                        if (userId != null) {
                            val userRef = databaseReference.child(userId)
                            val hashMap = HashMap<String, String>()
                            hashMap["id"] = userId
                            hashMap["imageURL"] = "default"
                            hashMap["username"] = username

                            userRef.setValue(hashMap)
                                .addOnCompleteListener { innerTask ->
                                    if (innerTask.isSuccessful) {
                                        // Registration successful
                                    } else {
                                        // Registration failed
                                    }
                                }
                        }
                    } else {
                        // Registration failed due to unsuccessful authentication
                    }
                }

            true // Assume registration is successful (will be updated by the callbacks)
        } catch (e: Exception) {
            false // Registration failed due to an exception
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()

            authResult.user != null // Return true if the user is not null (login successful)
        } catch (e: Exception) {
            false // Login failed due to an exception
        }
    }

    fun logout(): Boolean {
        return try {
            auth.signOut()
            true // Logout successful
        } catch (e: Exception) {
            false // Logout failed
        }
    }
}