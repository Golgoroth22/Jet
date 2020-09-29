package com.vfalin.jet.repositories

import com.vfalin.jet.db.pojo.MemberDB

interface MembersActivityRepository {

    suspend fun getMembers(
        hasInternetConnection: Boolean,
        onSuccess: (List<MemberDB>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}