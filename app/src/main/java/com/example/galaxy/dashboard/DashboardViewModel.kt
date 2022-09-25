package com.example.galaxy.dashboard

import androidx.lifecycle.ViewModel
import java.util.*

class DashboardViewModel : ViewModel() {

    var date = "--"

    init {
        initDate()
    }

    private fun initDate() {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        dateChanged(day, month + 1, year)
    }

    fun dateChanged(dayOfMonth: Int, month: Int, year: Int) {
        date = String.format("%d / %d / %d", dayOfMonth, month, year)
    }
}