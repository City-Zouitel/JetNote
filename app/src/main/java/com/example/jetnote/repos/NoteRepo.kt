package com.example.jetnote.repos

import com.example.jetnote.db.entities.note.Note

interface NoteRepo {


    suspend fun addNote(noteNote: Note)

    suspend fun editNote(noteNote: Note)

    suspend fun deleteNote(noteNote: Note)

    suspend fun deleteAllTrashedNotes()

}