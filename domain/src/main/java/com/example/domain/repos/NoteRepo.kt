package com.example.domain.repos

import com.example.local.model.Note


interface NoteRepo {


    suspend fun addNote(noteNote: Note)

    suspend fun editNote(noteNote: Note)

    suspend fun deleteNote(noteNote: Note)

    suspend fun deleteAllTrashedNotes()

}