package com.example.chatapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.chatapp.Channel.GroupData
import com.example.chatapp.repository.GroupRepository

class CreateGroupViewModel : ViewModel() {
    private val groupRepository = GroupRepository()

    fun saveGroup(groupData: GroupData, callback: (Boolean) -> Unit) {
        groupRepository.saveGroupData(groupData, callback)
    }
}
