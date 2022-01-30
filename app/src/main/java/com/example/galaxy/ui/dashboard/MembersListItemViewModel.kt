package com.example.galaxy.ui.dashboard

import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.galaxy.data.GalaxyRepository
import com.example.galaxy.data.entity.Members

class MembersListItemViewModel(private val repository: GalaxyRepository) : ViewModel() {

    val members : LiveData<List<Members>> = repository.members.asLiveData()

}