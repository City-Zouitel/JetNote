package city.zouitel.tasks.mapper

import city.zouitel.domain.model.NoteAndTask as OutNoteAndTask
import city.zouitel.tasks.mapper.base.Mapper
import city.zouitel.tasks.model.NoteAndTask as InNoteAndTask


class NoteAndTaskMapper: Mapper.Base<InNoteAndTask, OutNoteAndTask>  {
    override fun toView(data: OutNoteAndTask): InNoteAndTask = with((data)) {
        InNoteAndTask(noteUid, todoId)
    }

    override fun toDomain(data: InNoteAndTask): OutNoteAndTask = with(data) {
        OutNoteAndTask(noteUid, todoId)
    }
}