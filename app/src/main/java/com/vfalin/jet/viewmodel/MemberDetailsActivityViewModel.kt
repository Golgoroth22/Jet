package com.vfalin.jet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfalin.jet.db.pojo.MemberDB
import com.vfalin.jet.model.MemberUi
import com.vfalin.jet.model.UiResponse
import com.vfalin.jet.repositories.MemberDetailsActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemberDetailsActivityViewModel(private val repository: MemberDetailsActivityRepository) :
    ViewModel() {
    private val mMemberLiveData = MutableLiveData<UiResponse<MemberUi>>()
    val memberLiveData: LiveData<UiResponse<MemberUi>> = mMemberLiveData

    fun getMemberDetails(memberId: String) {
        mMemberLiveData.postValue(UiResponse(isLoading = true))
        viewModelScope.launch {
            withContext(this.coroutineContext + Dispatchers.IO) {
                repository.getMemberById(
                    memberId,
                    { response -> receiveSuccessfulResponse(response) },
                    { throwable -> receiveFailureResponse(throwable) })
            }
        }
    }

    private fun receiveSuccessfulResponse(response: MemberDB?) {
        mMemberLiveData.postValue(UiResponse(response?.convertTo(), isLoading = false))
    }

    private fun receiveFailureResponse(t: Throwable) {
        mMemberLiveData.postValue(UiResponse(isLoading = false, error = t))
    }
}