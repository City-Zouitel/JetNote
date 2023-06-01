package com.example.note.mapper

import com.example.links.mapper.LinkMapper
import com.example.domain.model.Note as OutNote
import com.example.note.mapper.base.Mapper
import com.example.note.model.Data
import com.example.tags.mapper.TagMapper
import com.example.tasks.mapper.TaskMapper
import com.example.note.model.Note as InNote

class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper,
    private val taskMapper: TaskMapper,
    private val linkMapper: LinkMapper
): Mapper.ReadOnly<InNote, OutNote> {
    override fun toView(data: OutNote): InNote = with(data){
        InNote(
            dataEntity = dataMapper.toView(dataEntity),
            tagEntities = tagEntities.map { tagMapper.toView(it) },
            taskEntities = taskEntities.map { taskMapper.toView(it) },
            linkEntities = linkEntities.map { linkMapper.toView(it) }
        )
    }
}