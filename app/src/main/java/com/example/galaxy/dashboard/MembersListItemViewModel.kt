package com.example.galaxy.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.galaxy.data.GalaxyRepository
import com.example.galaxy.data.entity.MemberInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MembersListItemViewModel @Inject constructor(private val repository: GalaxyRepository) :
    ViewModel() {

    fun getMembers(): LiveData<List<MemberInfo>> {
        return liveData { emit(emptyList()) }
    }
}