package com.example.galaxy.chitfund.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberMappingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fund: MemberMapping)

    @Query("select * from table_members_mapping")
    fun getAll(): Flow<List<MemberMapping>>

    @Query("select * from table_members_mapping where id=:id")
    fun get(id: Int): MemberMapping?

    @Query("select count(*) from table_members_mapping where member_id=:memberId")
    fun getChits(memberId: Int): Int

    @Query(
        "select table_members.id,table_members.name, " +
                "count(table_members_mapping.chit_number) as chitCount from table_members " +
                "join table_members_mapping on table_members.id = table_members_mapping.member_id " +
                "where table_members.id =:memberId group by table_members.id"
    )
    fun getMemberChits(memberId: Int): MemberAndChitInfo

    @Query(
        "select table_members.id,table_members.name, " +
                "count(table_members_mapping.chit_number) as chitCount from table_members " +
                "join table_members_mapping on table_members.id = table_members_mapping.member_id " +
                "group by table_members.id"
    )
    fun getMemberChits(): List<MemberAndChitInfo>

    @Query("delete from table_members_mapping where id=:id")
    fun delete(id: Int)

    @Query("delete from table_members_mapping")
    fun deleteAll()
}

data class MemberAndChitInfo(
    val id: Int,
    val name: String,
    val chitCount: Int,
)