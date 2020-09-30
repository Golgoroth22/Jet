package com.vfalin.jet.repositories

import com.vfalin.jet.db.pojo.MemberDB

interface MemberDetailsActivityRepository {

    suspend fun getMemberById(
        memberId: String,
        onSuccess: (MemberDB?) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}