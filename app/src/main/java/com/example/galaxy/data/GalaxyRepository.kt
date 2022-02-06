package com.example.galaxy.data

import androidx.annotation.WorkerThread
import com.example.galaxy.data.dao.AttendanceDao
import com.example.galaxy.data.dao.MembersDao
import com.example.galaxy.data.entity.Attendance
import javax.inject.Inject

class GalaxyRepository @Inject constructor(
    private val attendanceDao: AttendanceDao,
    private val membersDao: MembersDao
) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(attendance: Attendance) {
        attendanceDao.insert(attendance)
    }

    fun getMembers() = membersDao.getAll()
}
