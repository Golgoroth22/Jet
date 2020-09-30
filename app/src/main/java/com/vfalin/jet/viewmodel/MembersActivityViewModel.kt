package com.vfalin.jet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfalin.jet.R
import com.vfalin.jet.db.pojo.MemberDB
import com.vfalin.jet.di.Scopes
import com.vfalin.jet.model.MemberUi
import com.vfalin.jet.model.UiResponse
import com.vfalin.jet.repositories.MembersActivityRepository
import com.vfalin.jet.utils.ConnectivityLiveData
import com.vfalin.jet.utils.ResourceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import toothpick.Toothpick

class MembersActivityViewModel(private val repository: MembersActivityRepository) : ViewModel() {
    private val mMembersLiveData = MutableLiveData<UiResponse<List<MemberUi>>>()
    val membersLiveData: LiveData<UiResponse<List<MemberUi>>> = mMembersLiveData
    val internetLiveData: LiveData<Boolean> =
        Toothpick.openScope(Scopes.APP).getInstance(ConnectivityLiveData::class.java)
    private var hasInternetConnection = false
    private val noInternetMessage: String =
        Toothpick.openScope(Scopes.APP).getInstance(ResourceManager::class.java)
            .getString(R.string.app_no_internet_message)

    fun getMembers() {
        mMembersLiveData.postValue(UiResponse(isLoading = true))
        viewModelScope.launch {
            withContext(this.coroutineContext + Dispatchers.IO) {
                repository.getMembers(
                    hasInternetConnection,
                    { response -> receiveSuccessfulResponse(response) },
                    { throwable -> receiveFailureResponse(throwable) })
            }
        }
    }

    fun getMoreMembers() {
        if (hasInternetConnection) {
            repository.increaseOffset()
            getMembers()
        } else {
            mMembersLiveData.postValue(
                UiResponse(isLoading = false, error = Throwable(noInternetMessage))
            )
        }
    }

    private fun receiveSuccessfulResponse(response: List<MemberDB>) {
        mMembersLiveData.postValue(UiResponse(response.map { it.convertTo() }, isLoading = false))
    }

    private fun receiveFailureResponse(t: Throwable) {
        if (hasInternetConnection) {
            mMembersLiveData.postValue(UiResponse(isLoading = false, error = t))
        } else {
            mMembersLiveData.postValue(
                UiResponse(isLoading = false, error = Throwable(noInternetMessage))
            )
        }
    }

    fun switchInternetConnectionStatus(status: Boolean) {
        hasInternetConnection = status
    }
}