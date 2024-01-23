package city.zouitel.repository.mapper

import city.zouitel.repository.mapper.base.Mapper
import city.zouitel.repository.model.NoteAndTask as InNoteAndTask
import city.zouitel.domain.model.NoteAndTask as OutNoteAndTask

class NoteAndTaskMapper: Mapper.Base<InNoteAndTask, OutNoteAndTask> {
    override fun toRepository(data: OutNoteAndTask): InNoteAndTask = with(data){
        InNoteAndTask(noteUid, todoId)
    }

    override fun toDomain(data: InNoteAndTask): OutNoteAndTask = with(data){
        OutNoteAndTask(noteUid, todoId)
    }
}