package com.example.galaxy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "total_chitti_amount")
data class TotalAmount(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "mem_id") var memId: Int,
    @ColumnInfo(name = "total") var total: Int,
    @ColumnInfo(name = "balance") var balance: Int

)