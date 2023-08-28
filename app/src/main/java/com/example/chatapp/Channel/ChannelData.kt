package com.example.chatapp.Channel

data class GroupModel(
    val groupName: String = "",
    val groupImageUrl: String = ""
)
data class Message(
    val senderUid: String = "",
    val message: String = "",
)