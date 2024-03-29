package com.example.galaxy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "table_members", indices = [Index(value = ["name"], unique = true)])
data class MemberInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "name", collate = ColumnInfo.NOCASE)
    var name: String
)
