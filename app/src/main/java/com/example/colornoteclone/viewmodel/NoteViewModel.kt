package com.example.colornoteclone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colornoteclone.database.Note
import com.example.colornoteclone.repository.NotesRepository
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(val repository: NotesRepository) : ViewModel() {

    fun upsertNote(note:Note)=viewModelScope.launch {
        repository.upsertNote(note)
    }

    fun deleteNote(note: Note)=viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun getAllnotes() = repository.getAllnotes()


    fun getDAte():String{

        return SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault()).format(Date())
    }

}