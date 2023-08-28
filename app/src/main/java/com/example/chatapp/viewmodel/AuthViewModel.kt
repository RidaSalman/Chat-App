package com.example.chatapp.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val _logoutLiveData = MutableLiveData<Boolean>()
    val logoutLiveData: LiveData<Boolean>
        get() = _logoutLiveData

    fun signup(username: String, email: String, password: String, confirmPassword: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = authRepository.signup(username,email, password)
            withContext(Dispatchers.Main) {
                callback(success)
            }
        }
    }


    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = authRepository.login(email, password)
            withContext(Dispatchers.Main) {
                callback(success)
            }
        }
    }

    fun logout() {
        val isLoggedOut = authRepository.logout()

        // Update the LiveData to indicate the result of the logout
        _logoutLiveData.value = isLoggedOut
    }
}
