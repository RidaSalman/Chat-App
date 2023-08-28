package com.example.chatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.chatapp.auth.AuthViewModel
import com.example.chatapp.databinding.FragmentLogOutBinding
import com.example.chatapp.databinding.FragmentLoginBinding

class LogOutFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: FragmentLogOutBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_out, container, false)
        navController = findNavController()
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.buttonLogOut.setOnClickListener{

            viewModel.logout()
            // Observe the LiveData in the ViewModel to know when the logout is complete

        }
        viewModel.logoutLiveData.observe(viewLifecycleOwner) { isLoggedOut ->
            if (isLoggedOut) {
                navController.navigate(R.id.action_logOutFragment_to_loginFragment)
            }
        }

        return binding.root
    }

}