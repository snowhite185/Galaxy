package com.example.galaxy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance_table")
data class Attendance(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "mem_id") var memId: Int,
    @ColumnInfo(name = "date_id") var dateId: Int,
    @ColumnInfo(name = "present") var present: Boolean = false
)