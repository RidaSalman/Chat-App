package com.example.chatapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Channel.GroupData
import com.example.chatapp.R
import com.squareup.picasso.Picasso
import java.io.File

class ChatAdapter(private val context: Context, private val groupList: List<GroupData>): RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

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
    }
    class MyViewHolder(itemView: android.view.View):RecyclerView.ViewHolder(itemView){

        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle1)
        val image:ImageView = itemView.findViewById(R.id.profile_image)


        @SuppressLint("ResourceType")
        fun bind(group: GroupData) {
            titleTextView.text = group.groupName
            val cleanedImageUrl = group.groupImageUrl.removePrefix("file://")



            Picasso.get()
                .load(File(cleanedImageUrl))
                .into(image)
        }

    }
}