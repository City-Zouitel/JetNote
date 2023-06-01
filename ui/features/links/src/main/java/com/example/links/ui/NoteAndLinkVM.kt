package com.example.links.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.NoteAndLink as OutNoteAndLink
import com.example.domain.usecase.NoteAndLinkUseCase
import com.example.links.mapper.NoteAndLinkMapper
import com.example.links.model.NoteAndLink as InNoteAndLink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAndLinkVM @Inject constructor(
    private val getAll: NoteAndLinkUseCase.GetAllNotesAndLinks,
    private val delete: NoteAndLinkUseCase.DeleteNoteAndLink,
    private val mapper: NoteAndLinkMapper
): ViewModel() {

    private val _getAllNotesAndLinks = MutableStateFlow<List<OutNoteAndLink>>(emptyList())
    val getAllNotesAndLinks: StateFlow<List<OutNoteAndLink>>
        get() = _getAllNotesAndLinks
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAll.invoke().collect {
                _getAllNotesAndLinks.value.map { noteAndLink ->
                    mapper.toView(noteAndLink)
                }
            }
        }
    }

    fun deleteNoteAndLink(noteAndLink: InNoteAndLink) {
        viewModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(noteAndLink))
        }
    }
}