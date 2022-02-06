package com.example.galaxy.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.galaxy.data.entity.Members

@Dao
interface MembersDao {
    @Query("SELECT * FROM members_table")
    fun getAll(): LiveData<List<Members>>
}