package com.example.links

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.reposImpl.NoteAndLinkRepoImpl
import com.example.local.model.NoteAndLink
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
    private val repo: NoteAndLinkRepoImpl
): ViewModel() {

    private val _getAllNotesAndLinks = MutableStateFlow<List<NoteAndLink>>(emptyList())
    val getAllNotesAndLinks: StateFlow<List<NoteAndLink>>
        get() = _getAllNotesAndLinks
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllNotesAndLinks.collect {
                _getAllNotesAndLinks.value = it
            }
        }
    }

    fun addNoteAndLink(noteAndLink: NoteAndLink) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addNoteAndLink(noteAndLink)
        }
    }

    fun deleteNoteAndLink(noteAndLink: NoteAndLink) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNoteAndLink(noteAndLink)
        }
    }
}