package com.example.galaxy.chitfund.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_chit_fund")
data class ChitFundInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "duration")
    var duration: Int,

    @ColumnInfo(name = "meeting_freq")
    var meetingFreq: String,

    @ColumnInfo(name = "loan_allowed")
    var loanAllowed: Boolean = true,

    @ColumnInfo(name = "loan_interest")
    var loanInterest: Double,

    @ColumnInfo(name = "loan_payment_frequency")
    var loanPaymentFrequency: String,

    @ColumnInfo(name = "fine_absence")
    var fineForAbsence: Double,

    @ColumnInfo(name = "start_date")
    var startDate: String = "",

    @ColumnInfo(name = "end_date")
    var endDate: String = "",

    @ColumnInfo(name = "total_contribution")
    var totalContribution: Double = 0.0,

    @ColumnInfo(name = "total_fine")
    var totalFine: Double = 0.0,

    @ColumnInfo(name = "total_loan")
    var totalLoan: Double = 0.0,

    @ColumnInfo(name = "total_interest")
    var totalInterest: Double = 0.0,

    @ColumnInfo(name = "total_dividend")
    var totalDividend: Double = 0.0,

    @ColumnInfo(name = "current_meeting_count")
    var currentMeetingCount: Int = 0,

    @ColumnInfo(name = "premium")
    var premium: Double = 0.0,
)