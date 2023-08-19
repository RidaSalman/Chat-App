package com.example.chatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.chatapp.databinding.FragmentCreateNewGroupBinding
import com.example.chatapp.databinding.FragmentLoginBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateNewGroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateNewGroupFragment : Fragment() {
    private lateinit var binding: FragmentCreateNewGroupBinding
    private lateinit var navController: NavController
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



        return binding.root
    }
}