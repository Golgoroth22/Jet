package com.vfalin.jet.repositories

import com.vfalin.jet.db.dao.MembersDao
import com.vfalin.jet.db.pojo.MemberDB
import java.lang.Exception

class MemberDetailsActivityRepositoryImpl(
    private val membersDao: MembersDao
) : MemberDetailsActivityRepository {

    override suspend fun getMemberById(
        memberId: String,
        onSuccess: (MemberDB?) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            onSuccess(membersDao.getById(memberId))
        } catch (e: Exception) {
            onFailure.invoke(e)
        }
    }
}