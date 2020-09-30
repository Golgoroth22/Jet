package com.vfalin.jet.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vfalin.jet.repositories.MembersActivityRepository
import com.vfalin.jet.viewmodel.MembersActivityViewModel
import javax.inject.Inject

class MembersActivityViewModelFactory @Inject constructor(private val repository: MembersActivityRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MembersActivityViewModel(repository) as T
    }
}