package com.example.chatapp

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Adapter.ChatAdapter
import com.example.chatapp.Channel.GroupData
import com.example.chatapp.databinding.FragmentChannelBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChannelFragment : Fragment() {

    private lateinit var binding: FragmentChannelBinding
    private lateinit var navController: NavController
    private lateinit var adapter: ChatAdapter
    private lateinit var database: DatabaseReference
    private var groupList = ArrayList<GroupData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_channel, container, false)
        navController = findNavController()

        adapter = ChatAdapter(requireContext(), groupList)
        binding.chatshowRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.chatshowRecycler.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("groups")
        binding.floatingActionButton.setOnClickListener {
            navController.navigate(R.id.action_channelFragment_to_createNewGroupFragment)
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                groupList.clear()
                for (dataSnapshot in snapshot.children) {
                    val groupName = dataSnapshot.child("groupName").getValue(String::class.java)
                    groupName?.let {
                        val groupData = GroupData(groupName = it)
                        groupList.add(groupData)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled here
            }
        })

        /*val s = binding.searchView

        val hintText = "<font color='#B6B6B6'>${getString(R.string.hintSearchMess)}</font>"
        s = Html.fromHtml(hintText, Html.FROM_HTML_MODE_LEGACY)*/

        return binding.root
    }


}
