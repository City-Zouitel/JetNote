package city.zouitel.database.mapper

import city.zouitel.database.model.NoteAndReminderEntity as InNoteAndReminder
import city.zouitel.repository.model.NoteAndReminder as OutNoteAndReminder

class NoteAndReminderMapper {

    fun toRepo(notesAndReminder: List<InNoteAndReminder>) = notesAndReminder.map { toRepo(it) }

    fun toRepo(noteAndReminder: InNoteAndReminder) = OutNoteAndReminder(
        noteId = noteAndReminder.noteId,
        reminderId = noteAndReminder.reminderId
    )

    fun fromRepo(noteAndReminder: OutNoteAndReminder) = InNoteAndReminder(
        noteId = noteAndReminder.noteId,
        reminderId = noteAndReminder.reminderId
    )
}