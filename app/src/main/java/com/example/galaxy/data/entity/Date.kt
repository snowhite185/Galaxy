package com.example.galaxy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date_table")
data class Date(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "date") var date: String
)
