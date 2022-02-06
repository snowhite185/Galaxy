package com.example.galaxy.data

import android.content.Context
import androidx.room.Room
import com.example.galaxy.GalaxyApplication
import com.example.galaxy.data.dao.AttendanceDao
import com.example.galaxy.data.dao.MembersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAttendanceDao(appDatabase: GalaxyDatabase): AttendanceDao {
        return appDatabase.attendanceDao()
    }

    @Provides
    fun provideMembersDao(appDatabase: GalaxyDatabase): MembersDao {
        return appDatabase.membersDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): GalaxyDatabase {
        return Room.databaseBuilder(
            appContext,
            GalaxyDatabase::class.java,
            "galaxy_database"
        ).createFromAsset("database/galaxy_database.db").build()
    }
}