package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.NoteAndTaskEntity as InNoteAndTask
import com.example.repository.model.NoteAndTask as OutNoteAndTask

class NoteAndTaskMapper: Mapper.Base<InNoteAndTask, OutNoteAndTask> {
    override fun toLocal(data: OutNoteAndTask): InNoteAndTask = with(data){
        InNoteAndTask(noteUid, todoId)
    }

    override fun readOnly(data: InNoteAndTask): OutNoteAndTask = with(data){
        OutNoteAndTask(noteUid, todoId)
    }
}