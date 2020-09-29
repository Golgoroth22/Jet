package com.vfalin.jet.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vfalin.jet.db.dao.MembersDao
import com.vfalin.jet.db.pojo.MemberDB

@Database(entities = [MemberDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun membersDao(): MembersDao
}