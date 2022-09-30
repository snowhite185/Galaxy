package com.example.galaxy.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.galaxy.data.entity.MemberInfo

@Dao
interface MembersDao {
    @Query("SELECT * FROM table_members")
    fun getAll(): List<MemberInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(member: MemberInfo)

    @Query("select * from table_members where id=:id")
    fun get(id: Int): MemberInfo?

    @Query("delete from table_members where id=:id")
    fun delete(id: Int)

    @Query("delete from table_members")
    fun deleteAll()
}