package com.example.galaxy.chitfund.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContributionDao {

    @Insert
    fun insert(contribution: Contributions)

    @Query("select * from table_contributions")
    fun getAll(): Flow<List<Contributions>>

    @Query("select * from table_contributions where id=:id")
    fun get(id: Int): Contributions?

    @Query("delete from table_contributions where id=:id")
    fun delete(id: Int)

    @Query("delete from table_contributions")
    fun deleteAll()

    @Query("select max(meeting_count) from table_contributions where chit_fund_id=:fundId")
    fun getMeetingCount(fundId: Long): Int
}