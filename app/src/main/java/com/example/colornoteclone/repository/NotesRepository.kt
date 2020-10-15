package com.example.colornoteclone.repository

import com.example.colornoteclone.database.Note
import com.example.colornoteclone.database.NoteDatabase

class NotesRepository(private val db:NoteDatabase) {


        suspend fun upsertNote(note:Note)=db.getNoteDao().upsert(note)

        suspend fun deleteNote(note:Note)=db.getNoteDao().delete(note)

        fun getAllnotes()=db.getNoteDao().getAllNotes()
}