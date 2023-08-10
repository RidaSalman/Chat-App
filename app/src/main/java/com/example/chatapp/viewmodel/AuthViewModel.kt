package com.example.chatapp.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    fun signup(email: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = authRepository.signup(email, password)
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
}
