package com.example.galaxy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interest_table")
data class Interest(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "loan_id") var loanId: Int,
    @ColumnInfo(name = "date_id") var dateId: Int,
    @ColumnInfo(name = "amount_payed") var amountPayed: Int,
    @ColumnInfo(name = "payed") var payed: Boolean = false,
    @ColumnInfo(name = "interest_amount") var interestAmount: Int
)