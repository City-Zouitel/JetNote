package com.example.repository.mapper

import com.example.repository.mapper.base.Mapper
import com.example.repository.model.Note as InNote
import com.example.domain.model.Note as OutNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val taskMapper: TaskMapper,
    private val linkMapper: LinkMapper
): Mapper.ReadOnly<InNote, OutNote> {
    override fun toDomain(data: InNote): OutNote = with(data){
        OutNote(
            dataEntity = dataMapper.toDomain(dataEntity),
            tagEntities = tagEntities.map { tagMapper.toDomain(it) },
            taskEntities = taskEntities.map { taskMapper.toDomain(it) },
            linkEntities = linkEntities.map { linkMapper.toDomain(it) }
        )
    }
}