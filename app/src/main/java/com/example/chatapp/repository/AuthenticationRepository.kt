package com.example.chatapp.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class AuthenticationRepository(private val application: Application) {
    private val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val getFirebaseUserMutableLiveData: MutableLiveData<FirebaseUser>
        get() = firebaseUserMutableLiveData

    val getUserLoggedMutableLiveData: MutableLiveData<Boolean>
        get() = userLoggedMutableLiveData

    init {
        if (auth.currentUser != null) {
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
    }

    fun register(email: String, password: String, confirmpassword: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    firebaseUserMutableLiveData.postValue(auth.currentUser)
                } else {
                    Toast.makeText(application, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun login(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    firebaseUserMutableLiveData.postValue(auth.currentUser)
                } else {
                    Toast.makeText(application, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signOut() {
        auth.signOut()
        userLoggedMutableLiveData.postValue(true)
    }
}
