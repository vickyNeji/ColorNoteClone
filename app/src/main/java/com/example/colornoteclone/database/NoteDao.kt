package com.example.colornoteclone.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun upsert(note:Note)

        @Delete
        suspend fun delete(note: Note)

        @Query("SELECT * FROM note")
        fun getAllNotes():LiveData<List<Note>>
}