package com.example.galaxy.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.galaxy.data.entity.Attendance

@Dao
interface AttendanceDao {

    @Insert
    fun insert(attendance: Attendance)



//    @Update
//    fun update(note: Note)
//
//    @Delete
//    fun delete(note: Note)
//
//    @Query("delete from note_table")
//    fun deleteAllNotes()

//    @Query("select * from note_table order by priority desc")
//    fun getAllNotes(): LiveData<List<Note>>
}