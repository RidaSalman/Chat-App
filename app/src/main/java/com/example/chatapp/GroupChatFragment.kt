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
import com.example.chatapp.databinding.FragmentGroupChatBinding


class GroupChatFragment : Fragment() {
    private lateinit var binding: FragmentGroupChatBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_chat, container, false)
        navController = findNavController()

        binding.backBtn.setOnClickListener{
            navController.navigate(R.id.action_groupChatFragment_to_channelFragment)
        }
        return binding.root
    }

}