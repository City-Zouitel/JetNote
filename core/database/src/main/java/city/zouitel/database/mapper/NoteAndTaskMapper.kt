package city.zouitel.database.mapper

import city.zouitel.database.mapper.base.Mapper
import city.zouitel.database.model.NoteAndTaskEntity as InNoteAndTask
import city.zouitel.repository.model.NoteAndTask as OutNoteAndTask

class NoteAndTaskMapper: Mapper.Base<InNoteAndTask, OutNoteAndTask> {
    override fun toLocal(data: OutNoteAndTask): InNoteAndTask = with(data){
        InNoteAndTask(noteUid, todoId)
    }

    override fun readOnly(data: InNoteAndTask): OutNoteAndTask = with(data){
        OutNoteAndTask(noteUid, todoId)
    }
}