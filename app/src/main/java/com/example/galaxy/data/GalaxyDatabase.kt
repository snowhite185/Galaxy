package com.example.galaxy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.galaxy.chitfund.db.*
import com.example.galaxy.data.dao.AttendanceDao
import com.example.galaxy.data.dao.MembersDao
import com.example.galaxy.data.entity.Attendance
import com.example.galaxy.data.entity.MemberInfo

@Database(
    entities = [
        Attendance::class,
        MemberInfo::class,
        MemberMapping::class,
        ChitFundInfo::class,
        Contributions::class
    ],
    version = 1
)
abstract class GalaxyDatabase : RoomDatabase() {

    abstract fun attendanceDao(): AttendanceDao
    abstract fun membersDao(): MembersDao
    abstract fun membersMappingDao(): MemberMappingDao
    abstract fun contributionDao(): ContributionDao
    abstract fun chitFundDao(): ChitFundDao

    companion object {

        @Volatile
        private var INSTANCE: GalaxyDatabase? = null

        fun getDatabase(context: Context): GalaxyDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    GalaxyDatabase::class.java,
                    "galaxy_database"
                ).build()
            }.also { INSTANCE = it }
        }
    }
}