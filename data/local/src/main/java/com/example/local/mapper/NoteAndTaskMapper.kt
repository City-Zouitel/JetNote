package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.NoteAndTaskEntity
import com.example.repository.model.NoteAndTask

class NoteAndTaskMapper: Mapper.Base<NoteAndTaskEntity, NoteAndTask> {
    override fun toLocal(data: NoteAndTask): NoteAndTaskEntity = with(data){
        NoteAndTaskEntity(noteUid, todoId)
    }

    override fun readOnly(data: NoteAndTaskEntity): NoteAndTask = with(data){
        NoteAndTask(noteUid, todoId)
    }
}