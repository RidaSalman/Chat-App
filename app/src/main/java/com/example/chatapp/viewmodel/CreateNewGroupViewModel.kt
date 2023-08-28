package com.example.chatapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.repository.GroupRepository

class CreateNewGroupViewModel : ViewModel() {
    private val groupCreationStatus = MutableLiveData<Boolean>()
    private val groupRepository = GroupRepository()


    fun createNewGroup(groupName: String, groupImageUri: Uri) {
        groupRepository.uploadImageAndSaveGroup(groupName, groupImageUri, object : GroupRepository.Callback {
            override fun onSuccess() {
                groupCreationStatus.postValue(true)
            }

            override fun onFailure(e: Exception) {
                groupCreationStatus.postValue(false)
            }
        })
    }
}
