package com.example.chatapp.repository

import android.net.Uri
import com.example.chatapp.Channel.GroupModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class GroupRepository {
    fun uploadImageAndSaveGroup(groupName: String, groupImageUri: Uri, callback: Callback) {
        // Upload group image to Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("group_images/${groupImageUri.lastPathSegment}")
        val uploadTask = imageRef.putFile(groupImageUri)

        // Listen for upload success/failure
        uploadTask.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                // Save group name and image URL to Firebase Realtime Database
                val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("groups")
                val group = GroupModel(groupName, uri.toString())
                databaseRef.push().setValue(group)
                callback.onSuccess()
            }.addOnFailureListener { e -> callback.onFailure(e) }
        }.addOnFailureListener { e -> callback.onFailure(e) }
    }

    interface Callback {
        fun onSuccess()
        fun onFailure(e: Exception)
    }
}
