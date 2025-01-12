package city.zouitel.reminder.ui

import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.domain.usecase.ReminderUseCase
import city.zouitel.domain.utils.Action
import city.zouitel.domain.utils.withAsync
import city.zouitel.logic.withFlow
import city.zouitel.reminder.mapper.ReminderMapper
import city.zouitel.reminder.model.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("DeferredResultUnused")
class ReminderScreenModel(
    private val observeByUid: ReminderUseCase.ObserveByUid,
    private val insert: ReminderUseCase.Insert,
    private val deleteById: ReminderUseCase.DeleteById,
    private val deleteByUid: ReminderUseCase.DeleteByUid,
    private val mapper: ReminderMapper
): ScreenModel {

    private val _observeAllById = MutableStateFlow<List<Reminder>>(emptyList())
    val observeAllById: StateFlow<List<Reminder>> = _observeAllById.withFlow(emptyList())

    fun initializeReminders(uid: String) {
        withAsync {
            observeByUid(uid).collect { _observeAllById.value = mapper.fromDomain(it) }
        }
    }

    fun sendAction(act: Action) {
        when(act) {
            is Action.Insert<*> -> withAsync { insert(mapper.toDomain(act.data as Reminder)) }
            is Action.DeleteById -> withAsync { deleteById(act.id as Int) }
            is Action.DeleteByUid -> withAsync { deleteByUid(act.uid) }
            else -> throw NotImplementedError("Action not implemented: $act")
        }
    }
}