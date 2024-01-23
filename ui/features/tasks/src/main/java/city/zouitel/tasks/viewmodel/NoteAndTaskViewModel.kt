package city.zouitel.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.domain.usecase.NoteAndTaskUseCase
import city.zouitel.tasks.mapper.NoteAndTaskMapper
import city.zouitel.tasks.model.NoteAndTask as InNoteAndTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//@HiltViewModel
class NoteAndTaskViewModel /*@Inject*/ constructor(
    getAll: NoteAndTaskUseCase.GetAllNotesAndTask,
    private val add: NoteAndTaskUseCase.AddNoteAndTask,
    private val delete: NoteAndTaskUseCase.DeleteNoteAndTask,
    private val mapper: NoteAndTaskMapper
): ViewModel() {

    private val _getAllNotesAndTask = MutableStateFlow<List<InNoteAndTask>>(emptyList())
    val getAllNotesAndTask: StateFlow<List<InNoteAndTask>>
        get() = _getAllNotesAndTask
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        viewModelScope.launch {
            getAll.invoke().collect { list ->
                _getAllNotesAndTask.value = list.map { task -> mapper.toView(task) }
            }
        }
    }

    fun addNoteAndTaskItem(noteAndTodo: InNoteAndTask) = viewModelScope.launch(Dispatchers.IO) {
        add.invoke(mapper.toDomain(noteAndTodo))
    }
    fun deleteNoteAndTaskItem(noteAndTodo: InNoteAndTask) = viewModelScope.launch(Dispatchers.IO) {
        delete.invoke(mapper.toDomain(noteAndTodo))
    }
}