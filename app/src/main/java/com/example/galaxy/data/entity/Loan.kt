package com.example.galaxy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loan_table")
data class Loan(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "mem_id") var memId: Int,
    @ColumnInfo(name = "date_id") var dateId: Int,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "payed") var payed: Boolean = false,
    @ColumnInfo(name = "balance") var balance: Int,
    @ColumnInfo(name = "loan_return") var loanReturnAmount: Int
)