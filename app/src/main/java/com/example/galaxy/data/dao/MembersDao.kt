package com.example.galaxy.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.galaxy.data.entity.Members
import kotlinx.coroutines.flow.Flow

@Dao
interface MembersDao {
    @Query("SELECT * FROM members_table")
    fun getAll(): Flow<List<Members>>
}