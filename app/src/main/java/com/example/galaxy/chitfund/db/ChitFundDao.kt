package com.example.galaxy.chitfund.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChitFundDao {
    @Insert
    fun insert(fund: ChitFundInfo): Long

    @Query("select * from table_chit_fund")
    fun getAll(): List<ChitFundInfo>

    @Query("select * from table_chit_fund where id=:id")
    fun get(id: Int): ChitFundInfo?

    @Query(
        "select table_members_mapping.chit_number as chitNumber," +
                "table_members_mapping.fund_id as fundId," +
                "table_members_mapping.member_id as memberId," +
                "table_chit_fund.name as fundName," +
                "table_members.name as memberName " +
                "from table_members_mapping " +
                "left join table_members on table_members_mapping.member_id = table_members.id " +
                "left join table_chit_fund on table_chit_fund.id = table_members_mapping.fund_id " +
                "where table_members_mapping.fund_id=:fundId " +
                "group by table_members_mapping.chit_number"
    )
    fun getMembers(fundId: Long): List<MemberFundInfo>

    @Query("delete from table_chit_fund where id=:id")
    fun delete(id: Int)

    @Query("delete from table_chit_fund")
    fun deleteAll()
}