package city.zouitel.tasks.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteAndTaskUseCase
import city.zouitel.tasks.mapper.NoteAndTaskMapper
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.NoteAndTask as InNoteAndTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteAndTaskScreenModel(
    getAll: NoteAndTaskUseCase.GetAllNotesAndTask,
    private val add: NoteAndTaskUseCase.AddNoteAndTask,
    private val delete: NoteAndTaskUseCase.DeleteNoteAndTask,
    private val mapper: NoteAndTaskMapper
): ScreenModel {

    private val _getAllNotesAndTask: MutableStateFlow<List<NoteAndTask>> = MutableStateFlow(
        emptyList()
    )
    val getAllNotesAndTask: StateFlow<List<InNoteAndTask>>
        get() = _getAllNotesAndTask
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        screenModelScope.launch {
            getAll.invoke().collect { notesAndTask ->
                _getAllNotesAndTask.value = mapper.fromDomain(notesAndTask)
            }
        }
    }

    fun addNoteAndTaskItem(noteAndTodo: InNoteAndTask) = screenModelScope.launch(Dispatchers.IO) {
        add.invoke(mapper.toDomain(noteAndTodo))
    }
    fun deleteNoteAndTaskItem(noteAndTodo: InNoteAndTask) = screenModelScope.launch(Dispatchers.IO) {
        delete.invoke(mapper.toDomain(noteAndTodo))
    }
}