package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.relational.NoteEntity
import com.example.repository.model.Note

class WidgetMapper (
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val taskMapper: TaskMapper,
    private val linkMapper: LinkMapper
): Mapper.ReadOnly<NoteEntity, Note> {
    override fun readOnly(data: NoteEntity): Note = with(data) {
        Note(
            dataEntity = dataMapper.readOnly(dataEntity),
            tagEntities = tagEntities.map { tagMapper.readOnly(it) },
            taskEntities = taskEntities.map { taskMapper.readOnly(it) },
            linkEntities = linkEntities.map { linkMapper.readOnly(it) }
        )
    }
}