package com.example.galaxy

import android.app.Application
import com.example.galaxy.data.GalaxyDatabase
import com.example.galaxy.data.GalaxyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GalaxyApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { GalaxyDatabase.getDatabase(this) }
    val repository by lazy { GalaxyRepository(database.attendanceDao(), database.membersDao()) }

}