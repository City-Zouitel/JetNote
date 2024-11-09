package city.zouitel.repository.mapper

import city.zouitel.domain.model.NoteAndReminder as OutNoteAndReminder
import city.zouitel.repository.model.NoteAndReminder as InNoteAndReminder

class NoteAndReminderMapper {

    fun toDomain(notesAndReminder: List<InNoteAndReminder>) = notesAndReminder.map { toDomain(it) }

    fun toDomain(noteAndReminder: InNoteAndReminder) = OutNoteAndReminder(
        noteId = noteAndReminder.noteId,
        reminderId = noteAndReminder.reminderId
    )

    fun fromDomain(noteAndReminder: OutNoteAndReminder) = InNoteAndReminder(
        noteId = noteAndReminder.noteId,
        reminderId = noteAndReminder.reminderId
    )
}