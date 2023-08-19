package com.example.chatapp

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.chatapp.auth.AuthViewModel
import com.example.chatapp.databinding.FragmentSignupBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.checkerframework.checker.nullness.qual.NonNull


class SignupFragment : Fragment() {


    private lateinit var binding: FragmentSignupBinding
    private var mProfileUri: Uri? = null
    private lateinit var navController: NavController
    private lateinit var viewModel: AuthViewModel
    /*private lateinit var auth: FirebaseAuth*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        navController = findNavController()
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        /*auth = Firebase.auth*/
        binding.logintext.setOnClickListener{
            navController.navigate(R.id.action_signupFragment_to_loginFragment);
        }
        binding.floatingActionButton2.setOnClickListener {
            ImagePicker.with(this)
                .crop() //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
        binding.buttonsignup.setOnClickListener{
            performSignup()
        }


        return binding.root

    }

    private fun performSignup() {
        val username = binding.usernameSignup.text.toString()
        val email = binding.emailSignup.text.toString()
        val password = binding.passwordSignup.text.toString()
        val confirmPassword = binding.confirmpassword.text.toString()

        if(email.isEmpty() || password.isEmpty() || username.isEmpty() || confirmPassword.isEmpty()){
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confirmPassword) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.signup(username,email, password,confirmPassword) { success ->
            if (success) {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "Signup Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                navController.navigate(R.id.action_signupFragment_to_loginFragment)
            } else {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "Signup Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                mProfileUri = fileUri
                binding.profileImage.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }



}
