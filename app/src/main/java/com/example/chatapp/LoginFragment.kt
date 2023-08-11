package com.example.chatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.chatapp.auth.AuthViewModel
import com.example.chatapp.databinding.FragmentLoginBinding
import com.example.chatapp.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: AuthViewModel
    /*private lateinit var auth: FirebaseAuth*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        navController = findNavController()
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        // Initialize Firebase Auth
        /*auth = Firebase.auth*/
        binding.registertext.setOnClickListener{
            navController.navigate(R.id.action_loginFragment_to_signupFragment);
        }

        binding.buttonLogin.setOnClickListener{
            performlogin()
        }


        return binding.root
    }

    private fun performlogin() {
        val email = binding.emailLoginn.text.toString()
        val password = binding.passwordLoginn.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.login(email, password) { success ->
            if (success) {
                navController.navigate(R.id.action_loginFragment_to_channelFragment)
                Toast.makeText(
                    requireContext(),
                    "Login Successful",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Login Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}