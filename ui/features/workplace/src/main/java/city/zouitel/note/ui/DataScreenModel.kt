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
import city.zouitel.note.model.Data as InData

class DataScreenModel(
    private val add: DataUseCase.AddData,
    private val edit: DataUseCase.EditData,
    private val delete: DataUseCase.DeleteData,
    private val eraseTrash: DataUseCase.DeleteAllTrashedData,
    private val mapper: DataMapper
): ScreenModel, UiEventHandler<Data> {

    // for add a dataEntity from NoteEntityState as it to DataEntity class.
    fun addData(data: InData) {
        screenModelScope.launch(Dispatchers.IO) {
            add.invoke(mapper.toDomain(data))
        }
    }

    // for editData a dataEntity from NoteEntityState and put it to DataEntity class,
    // depending on changes.
    fun editData(data: InData){
        screenModelScope.launch(Dispatchers.IO) {
            edit.invoke(mapper.toDomain(data))
        }
    }

    // for deleting a dataEntity by the id.
    fun deleteData(data: InData){
        screenModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(data))
        }
    }

    //delete all removed notes.
    fun eraseNotes() {
        screenModelScope.launch(Dispatchers.IO) {
            eraseTrash.invoke()
        }
    }

    override fun sendUiEvent(event: UiEvent<Data>) {
        when(event) {
            is UiEvent.Delete -> performUiEvent { delete(mapper.toDomain(event.data)) }
            is UiEvent.Insert -> performUiEvent { add(mapper.toDomain(event.data)) }
            is UiEvent.Update -> performUiEvent { edit(mapper.toDomain(event.data)) }
            is UiEvent.DeleteAll -> performUiEvent { eraseTrash() }
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) {
            action.invoke()
        }
    }
}