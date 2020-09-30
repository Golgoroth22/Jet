package com.vfalin.jet.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vfalin.jet.repositories.MemberDetailsActivityRepository
import com.vfalin.jet.viewmodel.MemberDetailsActivityViewModel
import javax.inject.Inject

class MemberDetailsActivityViewModelFactory @Inject constructor(private val repository: MemberDetailsActivityRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MemberDetailsActivityViewModel(repository) as T
    }
}