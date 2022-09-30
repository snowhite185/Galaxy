package com.example.galaxy.di

import android.content.Context
import com.example.galaxy.chitfund.db.ChitFundDao
import com.example.galaxy.chitfund.db.MemberMappingDao
import com.example.galaxy.data.GalaxyDatabase
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
    fun provideChitFundDao(appDatabase: GalaxyDatabase): ChitFundDao {
        return appDatabase.chitFundDao()
    }

    @Provides
    fun provideMemberMappingDao(appDatabase: GalaxyDatabase): MemberMappingDao {
        return appDatabase.membersMappingDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): GalaxyDatabase {
        return GalaxyDatabase.getDatabase(appContext)
    }
}