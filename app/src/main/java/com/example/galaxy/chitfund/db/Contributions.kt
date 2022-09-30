package com.example.galaxy.chitfund.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_contributions")
data class Contributions(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "chit_number")
    var chitNumber: Int,

    @ColumnInfo(name = "chit_fund_id")
    var chitFundId: Int,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "meeting_count")
    var meetingCount: Int,

    @ColumnInfo(name = "amount_paid")
    var amountPaid: Double,

    @ColumnInfo(name = "fine_paid")
    var finePaid: Double,

    @ColumnInfo(name = "loan_interest_paid")
    var loanInterestPaid: Double,

    @ColumnInfo(name = "member_present")
    var memberPresent: Boolean,

    @ColumnInfo(name = "loan_capital_paid")
    var loanCapitalPaid: Double,

    )