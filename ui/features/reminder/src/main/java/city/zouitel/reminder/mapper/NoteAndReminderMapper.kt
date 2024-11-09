package city.zouitel.reminder.mapper

import city.zouitel.domain.model.NoteAndReminder as OutNoteAndReminder
import city.zouitel.reminder.model.NoteAndReminder as InNoteAndReminder

class NoteAndReminderMapper {

    fun fromDomain(notesAndReminder: List<OutNoteAndReminder>) = notesAndReminder.map { fromDomain(it) }

    fun toDomain(noteAndReminder: InNoteAndReminder) = OutNoteAndReminder(
        noteId = noteAndReminder.noteId,
        reminderId = noteAndReminder.reminderId
    )

    fun fromDomain(noteAndReminder: OutNoteAndReminder) = InNoteAndReminder(
        noteId = noteAndReminder.noteId,
        reminderId = noteAndReminder.reminderId
    )
}