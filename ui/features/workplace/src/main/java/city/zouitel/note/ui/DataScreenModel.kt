package city.zouitel.note.ui

import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.domain.usecase.MediaUseCase
import city.zouitel.domain.usecase.NoteAndTagUseCase
import city.zouitel.domain.usecase.ReminderUseCase
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.domain.utils.Action
import city.zouitel.domain.utils.withAsync
import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.model.Data

@Suppress("DeferredResultUnused")
class DataScreenModel(
    private val insert: DataUseCase.Insert,
    private val archive: DataUseCase.Archive,
    private val rollback: DataUseCase.Rollback,
    private val data: DataUseCase.Delete,
    private val erase: DataUseCase.Erase,
    private val noteAndTags: NoteAndTagUseCase.DeleteByUid,
    private val audio: AudioUseCase.DeleteByUid,
    private val link: LinkUseCase.DeleteByUid,
    private val media: MediaUseCase.DeleteByUid,
    private val reminder: ReminderUseCase.DeleteByUid,
    private val task: TaskUseCase.DeleteByUid,
    private val mapper: DataMapper
): ScreenModel {

    fun sendAction(act: Action) {
        when(act) {
            is Action.Insert<*> -> withAsync { insert(mapper.toDomain(act.data as Data)) }
            is Action.Archive -> withAsync { archive(act.uid) }
            is Action.Rollback -> withAsync { rollback(act.uid) }
            is Action.DeleteByUid -> withAsync {
                data(act.uid)
                noteAndTags(act.uid)
                audio(act.uid)
                link(act.uid)
                media(act.uid)
                reminder(act.uid)
                task(act.uid)
            }
            is Action.Erase -> withAsync { erase() }
            else -> throw NotImplementedError("This event is not implemented: $act")
        }
    }
}