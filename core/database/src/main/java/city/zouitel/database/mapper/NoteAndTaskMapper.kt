package city.zouitel.database.mapper

import city.zouitel.repository.model.NoteAndTask
import city.zouitel.database.model.NoteAndTaskEntity as InNoteAndTask
import city.zouitel.repository.model.NoteAndTask as OutNoteAndTask

class NoteAndTaskMapper {

    fun toRepo(notesAndTasks: List<InNoteAndTask>) = notesAndTasks.map { toRepo(it) }

    private fun toRepo(noteAndTask: InNoteAndTask) = OutNoteAndTask(
        noteUid = noteAndTask.noteUid,
        taskId = noteAndTask.taskId
    )

    fun fromRepo(noteAndTask: OutNoteAndTask) = InNoteAndTask(
        noteUid = noteAndTask.noteUid,
        taskId = noteAndTask.taskId
    )
}