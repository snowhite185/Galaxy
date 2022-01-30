package com.example.galaxy.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.galaxy.data.GalaxyRepository

class MemberListItemViewModelFactory(private val repository: GalaxyRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MembersListItemViewModel::class.java)) {
            return MembersListItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}