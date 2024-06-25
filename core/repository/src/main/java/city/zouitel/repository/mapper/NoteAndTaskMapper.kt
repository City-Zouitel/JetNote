package city.zouitel.repository.mapper

import city.zouitel.repository.model.NoteAndTask as InNoteAndTask
import city.zouitel.domain.model.NoteAndTask as OutNoteAndTask

class NoteAndTaskMapper {

    fun toDomain(notesAndTasks: List<InNoteAndTask>) = notesAndTasks.map { toDomain(it) }

    private fun toDomain(noteAndTask: InNoteAndTask) = OutNoteAndTask(
        noteUid = noteAndTask.noteUid,
        taskId = noteAndTask.taskId
    )

    fun fromDomain(noteAndTask: OutNoteAndTask) = InNoteAndTask(
        noteUid = noteAndTask.noteUid,
        taskId = noteAndTask.taskId
    )
}