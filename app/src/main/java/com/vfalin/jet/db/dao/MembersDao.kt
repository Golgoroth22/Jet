package com.vfalin.jet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vfalin.jet.db.pojo.MemberDB


@Dao
interface MembersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<MemberDB>)

    @Query("SELECT * FROM members WHERE _id LIKE (:id) LIMIT 1")
    suspend fun getById(id: String): MemberDB?

    @Query("SELECT * FROM members")
    suspend fun getMembers(): List<MemberDB>
}