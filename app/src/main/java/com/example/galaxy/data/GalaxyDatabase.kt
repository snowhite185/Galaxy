package com.example.galaxy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.galaxy.data.dao.AttendanceDao
import com.example.galaxy.data.dao.MembersDao
import com.example.galaxy.data.entity.Attendance
import com.example.galaxy.data.entity.Members

@Database(entities = [Attendance::class, Members::class], version = 1)
abstract class GalaxyDatabase : RoomDatabase() {

    abstract fun attendanceDao(): AttendanceDao
    abstract fun membersDao(): MembersDao
}