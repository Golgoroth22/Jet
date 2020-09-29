package com.vfalin.jet.db.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vfalin.jet.model.MemberUi
import com.vfalin.jet.utils.Constants

@Entity(tableName = "members")
data class MemberDB(
    @PrimaryKey
    @ColumnInfo(name = "_id") val id: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "utc_offset") val utcOffset: Double?,
    @ColumnInfo(name = "username") val username: String?
) {
    fun convertTo() = MemberUi(id, status, name, utcOffset, username)
}