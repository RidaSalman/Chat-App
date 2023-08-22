package com.example.chatapp.repository

import com.example.chatapp.Channel.GroupData
import com.google.firebase.database.FirebaseDatabase

class GroupRepository {
    private val databaseReference = FirebaseDatabase.getInstance().reference

    fun saveGroupData(groupData: GroupData, callback: (Boolean) -> Unit) {
        val groupId = databaseReference.child("groups").push().key ?: ""
        databaseReference.child("groups").child(groupId).setValue(groupData)
            .addOnCompleteListener { task ->
                callback.invoke(task.isSuccessful)
            }
    }
}
