package com.example.chatapp

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Adapter.ChatAdapter
import com.example.chatapp.databinding.FragmentChannelBinding


class ChannelFragment : Fragment() {

    private lateinit var binding: FragmentChannelBinding
    private lateinit var navController: NavController
    private lateinit var adapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        adapter = ChatAdapter()
        navController = findNavController()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_channel, container, false)
        binding.chatshowRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.chatshowRecycler.adapter = adapter
        binding.floatingActionButton.setOnClickListener{
            navController.navigate(R.id.action_channelFragment_to_createNewGroupFragment);
        }

        /*val s = binding.searchView

        val hintText = "<font color='#B6B6B6'>${getString(R.string.hintSearchMess)}</font>"
        s = Html.fromHtml(hintText, Html.FROM_HTML_MODE_LEGACY)*/


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }


}