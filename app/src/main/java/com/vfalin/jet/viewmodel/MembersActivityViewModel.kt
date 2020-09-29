package com.vfalin.jet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfalin.jet.di.Scopes
import com.vfalin.jet.model.MemberUi
import com.vfalin.jet.model.UiResponse
import com.vfalin.jet.network.pojo.MembersResponse
import com.vfalin.jet.repositories.MembersActivityRepository
import com.vfalin.jet.utils.ConnectivityLiveData
import com.vfalin.jet.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import toothpick.Toothpick

class MembersActivityViewModel(private val repository: MembersActivityRepository) : ViewModel() {
    private val mMembersLiveData = MutableLiveData<UiResponse<List<MemberUi>>>()
    val membersLiveData: LiveData<UiResponse<List<MemberUi>>> = mMembersLiveData
    val internetLiveData: LiveData<Boolean> =
        Toothpick.openScope(Scopes.APP).getInstance(ConnectivityLiveData::class.java)
    private var hasInternetConnection = false

    fun getMembers() {
        mMembersLiveData.postValue(UiResponse(isLoading = true))
        viewModelScope.launch {
            withContext(this.coroutineContext + Dispatchers.IO) {
                repository.getMembers(
                    Constants.USER_ID,
                    Constants.USER_TOKEN,
                    { response -> receiveSuccessfulResponse(response) },
                    { throwable -> receiveFailureResponse(throwable) })
            }
        }
    }

    private fun receiveSuccessfulResponse(response: MembersResponse) {
        mMembersLiveData.postValue(
            UiResponse(
                response.members.map { it.convertTo() },
                isLoading = false
            )
        )
        Timber.i("MembersActivityViewModel receiveSuccessfulResponse $response")
    }

    private fun receiveFailureResponse(t: Throwable) {
        if (hasInternetConnection) {
            mMembersLiveData.postValue(UiResponse(isLoading = false, error = t))
        } else {
            mMembersLiveData.postValue(
                UiResponse(
                    isLoading = false,
                    error = Throwable("Отсутствует интернет соединение")
                )
            )
        }
        Timber.e("MembersActivityViewModel receiveFailureResponse ${t.localizedMessage}")
    }

    fun switchInternetConnectionStatus(status: Boolean) {
        hasInternetConnection = status
    }
}