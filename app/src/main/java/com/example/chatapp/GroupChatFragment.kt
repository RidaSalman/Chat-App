package com.example.chatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Adapter.ChatAdapter
import com.example.chatapp.Adapter.MessageAdapter
import com.example.chatapp.Channel.Message
import com.example.chatapp.databinding.FragmentCreateNewGroupBinding
import com.example.chatapp.databinding.FragmentGroupChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


class GroupChatFragment : Fragment() {
    private lateinit var binding: FragmentGroupChatBinding
    private lateinit var navController: NavController
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList : ArrayList<Message>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_chat, container, false)
        navController = findNavController()

        binding.backBtn.setOnClickListener{
            navController.navigate(R.id.action_groupChatFragment_to_channelFragment)
        }
        
        
        val groupName = arguments?.getString("groupName", "")
        val groupImage = arguments?.getString("groupImage", "")

        binding.grouptextname.text = groupName

        if (groupImage?.startsWith("http") == true) {
            Picasso.get().load(groupImage).into(binding.grouppic)
        } else {
            // Load image from local file path if needed
        }

        messageList = ArrayList()
        messageAdapter = MessageAdapter(requireContext(),messageList)

        binding.groupchatrecycler.layoutManager= LinearLayoutManager(requireContext())
        binding.groupchatrecycler.adapter = messageAdapter

        // Use the group name to form the child reference for messages
        val groupMessageReference = FirebaseDatabase.getInstance().reference
            .child("messages")
            .child(groupName ?: "")

        // Fetch existing messages and populate the messageList
        groupMessageReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let { messageList.add(it) }
                }
                messageAdapter.notifyDataSetChanged()
                // Scroll to the last item after updating the list
                binding.groupchatrecycler.scrollToPosition(messageList.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled here
            }
        })

        binding.sendBtn.setOnClickListener {
            val messageText = binding.sendmessage.text.toString().trim()

            if (messageText.isNotEmpty()) {
                // Create a new Message object with sender UID and message text
                val newMessage = Message(
                    senderUid = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                    message = messageText
                )

                // Add the new message to the messageList
                messageList.add(newMessage)

                // Notify the adapter about the data change
                messageAdapter.notifyDataSetChanged()

                // Scroll the RecyclerView to the last item
                binding.groupchatrecycler.scrollToPosition(messageList.size - 1)

                // Clear the text in the EditText
                binding.sendmessage.text.clear()

                // Push the new message to the Firebase Realtime Database under the group
                groupMessageReference.push().setValue(newMessage)
            }
        }

        // Listen for new messages using ChildEventListener
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val newMessage = snapshot.getValue(Message::class.java)
                newMessage?.let {
                    messageList.add(it)
                    messageAdapter.notifyItemInserted(messageList.size - 1)
                    // Scroll to the last item after adding the new message
                    binding.groupchatrecycler.scrollToPosition(messageList.size - 1)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Not needed for messages
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // Not needed for messages
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Not needed for messages
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled here
            }
        }

        groupMessageReference.addChildEventListener(childEventListener)

        return binding.root
    }

}