package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.relational.NoteEntity as InNote
import com.example.repository.model.Note as OutNote

class WidgetMapper (
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val taskMapper: TaskMapper,
    private val linkMapper: LinkMapper
): Mapper.ReadOnly<InNote, OutNote> {
    override fun readOnly(data: InNote): OutNote = with(data) {
        OutNote(
            dataEntity = dataMapper.readOnly(dataEntity),
            tagEntities = tagEntities.map { tagMapper.readOnly(it) },
            taskEntities = taskEntities.map { taskMapper.readOnly(it) },
            linkEntities = linkEntities.map { linkMapper.readOnly(it) }
        )
    }
}