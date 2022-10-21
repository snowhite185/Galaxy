package com.example.galaxy.chitfund.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_members_mapping",
    indices = [Index(value = ["member_id", "fund_id", "chit_number"], unique = true)]
)
data class MemberMapping(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "member_id")
    var memberId: Long,

    @ColumnInfo(name = "fund_id")
    var fundId: Long,

    @ColumnInfo(name = "chit_number")
    var chitNumber: Long,
)