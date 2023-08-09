package com.example.chatapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AuthenticationRepository
    val userData: MutableLiveData<FirebaseUser>
    val loggedStatus: MutableLiveData<Boolean>

    init {
        repository = AuthenticationRepository(application)
        userData = repository.getFirebaseUserMutableLiveData
        loggedStatus = repository.getUserLoggedMutableLiveData
    }

    fun register(email: String?, password: String?, confirmpassword: String?) {
        repository.register(email!!, password!!, confirmpassword!!)
    }

    fun signIn(email: String?, pass: String?) {
        repository.login(email!!, pass!!)
    }

    fun signOut() {
        repository.signOut()
    }
}