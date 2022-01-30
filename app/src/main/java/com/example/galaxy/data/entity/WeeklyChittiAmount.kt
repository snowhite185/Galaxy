package com.example.galaxy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_chitti_table")
data class WeeklyChittiAmount(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "date_id") var dateId: Int,
    @ColumnInfo(name = "mem_id") var memId: Int,
    @ColumnInfo(name = "amount") var amount: Int

)