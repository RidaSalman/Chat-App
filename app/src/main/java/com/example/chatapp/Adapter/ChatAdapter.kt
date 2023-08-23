package com.example.chatapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Channel.GroupModel
import com.example.chatapp.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class ChatAdapter(private val context: Context, private val groupList: List<GroupModel>): RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_show,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val group = groupList[position]
        holder.bind(group)

        holder.itemView.setOnClickListener {
            // Navigate to the GroupChatFragment action with the bundle as argument
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.action_channelFragment_to_groupChatFragment)
        }


    }
    class MyViewHolder(itemView: android.view.View):RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle1)
        val image: ImageView = itemView.findViewById(R.id.profile_image)

        @SuppressLint("ResourceType")
        fun bind(group: GroupModel) {
            titleTextView.text = group.groupName

            if (group.groupImageUrl.startsWith("http")) {
                // Load image from URL
                Picasso.get().load(group.groupImageUrl).into(image)
            } else {
                // Load image from local file path
                val cleanedImageUrl = group.groupImageUrl.removePrefix("file://")
                val file = File(cleanedImageUrl)
                val uri = file.toURI()
                Picasso.get().load(uri.toURL().toString()).into(image)
            }
        }


    }
}
