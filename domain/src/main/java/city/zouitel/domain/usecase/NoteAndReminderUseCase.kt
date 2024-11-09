package city.zouitel.domain.usecase

import city.zouitel.domain.model.NoteAndReminder
import city.zouitel.domain.repository.NoteAndReminderRepo

sealed class NoteAndReminderUseCase {

    data class ObserveAllNotesAndReminders(private val repository: NoteAndReminderRepo): NoteAndReminderUseCase() {
        operator fun invoke() = repository.observeAllNotesAndReminders
    }
    data class ObserveNoteAndRemindersById(private val repository: NoteAndReminderRepo): NoteAndReminderUseCase() {
        suspend operator fun invoke(noteId: String) = repository.observeNoteAndRemindersById(noteId)
    }
    data class InsertNoteAndReminder(private val repository: NoteAndReminderRepo): NoteAndReminderUseCase() {
        suspend operator fun invoke(noteAndReminder: NoteAndReminder) = repository.insertNoteAndReminder(noteAndReminder)
    }
    data class UpdateNoteAndReminder(private val repository: NoteAndReminderRepo): NoteAndReminderUseCase() {
        suspend operator fun invoke(noteAndReminder: NoteAndReminder) = repository.updateNoteAndReminder(noteAndReminder)
    }
    data class DeleteNoteAndReminderById(private val repository: NoteAndReminderRepo): NoteAndReminderUseCase() {
        suspend operator fun invoke(noteId: String) = repository.deleteNoteAndReminderById(noteId)
    }
    data class DeleteAllNotesAndReminders(private val repository: NoteAndReminderRepo): NoteAndReminderUseCase() {
        suspend operator fun invoke() = repository.deleteAllNotesAndReminders()
    }
}