package com.example.repository.mapper

import com.example.repository.mapper.base.Mapper
import com.example.repository.model.NoteAndTask as InNoteAndTask
import com.example.domain.model.NoteAndTask as OutNoteAndTask

class NoteAndTaskMapper: Mapper.Base<InNoteAndTask, OutNoteAndTask> {
    override fun toRepository(data: OutNoteAndTask): InNoteAndTask = with(data){
        InNoteAndTask(noteUid, todoId)
    }

    override fun readOnly(data: InNoteAndTask): OutNoteAndTask = with(data){
        OutNoteAndTask(noteUid, todoId)
    }
}