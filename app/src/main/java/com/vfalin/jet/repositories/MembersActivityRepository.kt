package com.vfalin.jet.repositories

import com.vfalin.jet.network.pojo.MembersResponse

interface MembersActivityRepository {
    suspend fun getMembers(
        userId: String,
        token: String,
        onSuccess: (MembersResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}