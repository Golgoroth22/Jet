package com.vfalin.jet.repositories

import com.vfalin.jet.db.dao.MembersDao
import com.vfalin.jet.db.pojo.MemberDB
import com.vfalin.jet.interactors.OffsetStorage
import com.vfalin.jet.network.services.MembersService
import com.vfalin.jet.utils.Constants

class MembersActivityRepositoryImpl(
    private val membersService: MembersService,
    private val membersDao: MembersDao,
    private val offsetStorage: OffsetStorage
) : MembersActivityRepository {

    override fun increaseOffset() {
        offsetStorage.increaseOffset()
    }

    override suspend fun getMembers(
        hasInternetConnection: Boolean,
        onSuccess: (List<MemberDB>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            if (hasInternetConnection) {
                val members =
                    membersService.getMembers(
                        Constants.USER_ID,
                        Constants.USER_TOKEN,
                        offsetStorage.getOffset()
                    )
                membersDao.insertUsers(members.members.map { it.convertTo() })
                onSuccess.invoke(membersDao.getMembers())
            } else {
                onSuccess.invoke(membersDao.getMembers())
            }
        } catch (e: Exception) {
            onFailure.invoke(e)
        }
    }
}