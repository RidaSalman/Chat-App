package com.example.chatapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R

class ChatAdapter: RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_show,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }
    class MyViewHolder(itemView: android.view.View):RecyclerView.ViewHolder(itemView){

    }
}