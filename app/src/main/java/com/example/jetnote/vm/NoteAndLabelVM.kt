package com.example.jetnote.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnote.db.entities.note_and_label.NoteAndLabel
import com.example.jetnote.reposImp.NoteAndLabelRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAndLabelVM @Inject constructor(
    private val repo: NoteAndLabelRepoImp
): ViewModel() {

    private val _getAllNotesAndLabels = MutableStateFlow<List<NoteAndLabel>>(emptyList())
    val getAllNotesAndLabels: StateFlow<List<NoteAndLabel>>
        get() = _getAllNotesAndLabels.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllNotesAndLabels.collect {
                _getAllNotesAndLabels.value = it
            }
        }
    }
    fun addNoteAndLabel(noteAndLabel: NoteAndLabel) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addNoteAndLabel(noteAndLabel)
        }
    }
    fun deleteNoteAndLabel(noteAndLabel: NoteAndLabel) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNoteAndLabel(noteAndLabel)
        }
    }
}