package com.example.chatapp

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.chatapp.Channel.GroupData
import com.example.chatapp.databinding.FragmentCreateNewGroupBinding
import com.example.chatapp.databinding.FragmentLoginBinding
import com.example.chatapp.viewmodel.CreateGroupViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase


class CreateNewGroupFragment : Fragment() {
    private lateinit var binding: FragmentCreateNewGroupBinding
    private lateinit var navController: NavController
    private var mProfileUri: Uri? = null
    private val viewModel: CreateGroupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_new_group, container, false)
        navController = findNavController()
        binding.backArrow.setOnClickListener{
            navController.navigate(R.id.action_createNewGroupFragment_to_channelFragment)
        }
        binding.floatingActionButton0.setOnClickListener {
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
        binding.buttoncreategroup.setOnClickListener {
            saveToDatabase()
            navController.navigate(R.id.action_createNewGroupFragment_to_channelFragment);
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

    private fun saveToDatabase() {
        val groupName = binding.groupname.text.toString().trim()

        if (groupName.isNotEmpty() && mProfileUri != null) {
            val groupData = GroupData(groupName = groupName, groupImageUrl = mProfileUri.toString())

            viewModel.saveGroup(groupData) { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(
                        requireContext(),
                        "Group created and data saved.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to save data to the database.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Please select an image and enter a group name.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}