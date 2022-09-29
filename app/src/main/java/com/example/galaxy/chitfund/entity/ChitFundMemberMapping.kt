package com.example.galaxy.chitfund.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_chit_fund_members_mapping")
data class ChitFundMemberMapping(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "member_id")
    var memberId: Int,

    @ColumnInfo(name = "fund_id")
    var fundId: Int,

    @ColumnInfo(name = "chittal_id")
    var chittalId: Int,
)