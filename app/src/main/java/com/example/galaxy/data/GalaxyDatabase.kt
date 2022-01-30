package com.example.galaxy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.galaxy.data.dao.AttendanceDao
import com.example.galaxy.data.dao.MembersDao
import com.example.galaxy.data.entity.Attendance
import com.example.galaxy.data.entity.Members

@Database(entities = [Attendance::class,Members::class], version = 1, exportSchema = false)
abstract class GalaxyDatabase : RoomDatabase() {

    abstract fun attendanceDao(): AttendanceDao
    abstract fun membersDao():MembersDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: GalaxyDatabase? = null

        fun getDatabase(context: Context): GalaxyDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GalaxyDatabase::class.java,
                    "word_database"
                ).createFromAsset("database/members_table.sql").build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}