package com.vfalin.jet.repositories

import com.vfalin.jet.db.pojo.MemberDB

interface MembersActivityRepository {

    fun increaseOffset()

    suspend fun getMembers(
        hasInternetConnection: Boolean,
        onSuccess: (List<MemberDB>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}