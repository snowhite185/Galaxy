package com.example.galaxy.chitfund.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChitFundDao {
    @Insert
    fun insert(fund: ChitFundInfo): Long

    @Query("select * from table_chit_fund")
    fun getAll(): Flow<List<ChitFundInfo>>

    @Query("select * from table_chit_fund where id=:id")
    fun get(id: Int): ChitFundInfo?

    @Query("delete from table_chit_fund where id=:id")
    fun delete(id: Int)

    @Query("delete from table_chit_fund")
    fun deleteAll()
}