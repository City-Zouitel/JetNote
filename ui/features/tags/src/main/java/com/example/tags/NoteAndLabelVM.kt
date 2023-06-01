package com.example.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.NoteAndTagUseCase
import com.example.tags.mapper.NoteAndTagMapper
import com.example.tags.model.NoteAndTag as InNoteAndTag
import com.example.domain.model.NoteAndTag as OutNoteAndTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAndLabelVM @Inject constructor(
    private val getAll: NoteAndTagUseCase.GetAllNotesAndTags,
    private val add: NoteAndTagUseCase.AddNoteAndTag,
    private val delete: NoteAndTagUseCase.DeleteNoteAndTag,
    private val mapper: NoteAndTagMapper
): ViewModel() {

    private val _getAllNotesAndLabels = MutableStateFlow<List<OutNoteAndTag>>(emptyList())
    val getAllNotesAndLabels: StateFlow<List<OutNoteAndTag>>
        get() = _getAllNotesAndLabels
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAll.invoke().collect {
                _getAllNotesAndLabels.value.map { tag ->
                    mapper.toView(tag)
                }
            }
        }
    }
    fun addNoteAndLabel(noteAndTag: InNoteAndTag) {
        viewModelScope.launch(Dispatchers.IO) {
            add.invoke(mapper.toDomain(noteAndTag))
        }
    }
    fun deleteNoteAndLabel(noteAndTag: InNoteAndTag) {
        viewModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(noteAndTag))
        }
    }
}