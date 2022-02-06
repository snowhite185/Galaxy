package com.example.galaxy.data

import androidx.annotation.WorkerThread
import com.example.galaxy.GalaxyApplication
import com.example.galaxy.data.dao.AttendanceDao
import com.example.galaxy.data.dao.MembersDao
import com.example.galaxy.data.entity.Attendance
import com.example.galaxy.data.entity.Members
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class GalaxyRepository @Inject constructor(private val attendanceDao: AttendanceDao, private val membersDao: MembersDao) {


    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
//    val allAttendee: Flow<List<Attendance>> = attendanceDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(attendance: Attendance) {
        attendanceDao.insert(attendance)
    }

    val members: Flow<List<Members>> = membersDao.getAll()
}
