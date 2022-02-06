package com.example.galaxy.ui.dashboard

import android.provider.MediaStore
import androidx.lifecycle.*
import com.example.galaxy.data.GalaxyRepository
import com.example.galaxy.data.entity.Members
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MembersListItemViewModel @Inject constructor(private val repository: GalaxyRepository) :
    ViewModel() {

    fun getMembers(): LiveData<List<Members>> {
        return repository.members.asLiveData()
    }
}