package com.vfalin.jet.repositories

import com.vfalin.jet.network.pojo.MembersResponse
import com.vfalin.jet.network.services.MembersService

class MembersActivityRepositoryImpl(
    private val membersService: MembersService
) : MembersActivityRepository {

    override suspend fun getMembers(
        userId: String,
        token: String,
        onSuccess: (MembersResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            onSuccess.invoke(membersService.getMembers(userId, token))
        } catch (e: Exception) {
            onFailure.invoke(e)
        }
    }
}