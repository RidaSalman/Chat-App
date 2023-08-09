package com.example.chatapp

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.chatapp.databinding.FragmentSignupBinding
import com.example.chatapp.viewmodel.AuthViewModel
import com.github.dhaval2404.imagepicker.ImagePicker


class SignupFragment : Fragment() {


    private lateinit var binding: FragmentSignupBinding
    private var mProfileUri: Uri? = null
    private lateinit var navController: NavController
    private lateinit var viewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(AuthViewModel::class.java)


        // Initialize the navController
        navController = findNavController()

        viewModel.userData.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                // Navigate to another fragment
                navController.navigate(R.id.action_loginFragment_to_signupFragment)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(AuthViewModel::class.java)
        binding.viewModel = viewModel
        navController = findNavController()

        binding.viewModel.userData.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                // Navigate to another fragment
                navController.navigate(R.id.action_loginFragment_to_signupFragment)
            }
        })

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
        binding.buttonSignup.setOnClickListener {
            val username = binding.usernameSignup.text.toString()
            val password = binding.passwordSignup.text.toString()
            val confirmPassword = binding.confirmpassword.text.toString()

            if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                if (password == confirmPassword) {
                    viewModel.register(username, password, confirmPassword)
                    navController.navigate(R.id.action_loginFragment_to_signupFragment)
                } else {
                    // Show error message to the user indicating password mismatch
                    Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Show error message indicating missing fields
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root

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
