package city.zouitel.tasks.mapper

import city.zouitel.domain.model.NoteAndTask as OutNoteAndTask
import city.zouitel.tasks.mapper.base.Mapper
import city.zouitel.tasks.model.NoteAndTask as InNoteAndTask


class NoteAndTaskMapper {

    fun fromDomain(notesAndTasks: List<OutNoteAndTask>) = notesAndTasks.map { fromDomain(it) }

    fun toDomain(noteAndTask: InNoteAndTask) = OutNoteAndTask(
         noteUid = noteAndTask.noteUid,
        taskId = noteAndTask.taskId
    )

    fun fromDomain(noteAndTask: OutNoteAndTask) = InNoteAndTask(
        noteUid = noteAndTask.noteUid,
        taskId = noteAndTask.taskId
    )
}