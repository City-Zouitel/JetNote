package com.example.tasks.mapper

import com.example.domain.model.NoteAndTask as OutNoteAndTask
import com.example.tasks.mapper.base.Mapper
import com.example.tasks.model.NoteAndTask as InNoteAndTask


class NoteAndTaskMapper: Mapper.Base<InNoteAndTask, OutNoteAndTask>  {
    override fun toView(data: OutNoteAndTask): InNoteAndTask = with((data)) {
        InNoteAndTask(noteUid, todoId)
    }

    override fun toDomain(data: InNoteAndTask): OutNoteAndTask = with(data) {
        OutNoteAndTask(noteUid, todoId)
    }
}