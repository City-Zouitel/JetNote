package city.zouitel.note.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataScreenModel(
    private val add: DataUseCase.AddData,
    private val edit: DataUseCase.EditData,
    private val delete: DataUseCase.DeleteData,
    private val eraseTrash: DataUseCase.DeleteAllTrashedData,
    private val mapper: DataMapper
): ScreenModel, UiEventHandler<Data> {

    override fun sendUiEvent(event: UiEvent<Data>) {
        when(event) {
            is UiEvent.Delete -> performUiEvent { delete(mapper.toDomain(event.data)) }
            is UiEvent.Insert -> performUiEvent { add(mapper.toDomain(event.data)) }
            is UiEvent.Update -> performUiEvent { edit(mapper.toDomain(event.data)) }
            is UiEvent.DeleteAll -> performUiEvent { eraseTrash() }
            else -> throw NotImplementedError("This event is not implemented: $event")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}