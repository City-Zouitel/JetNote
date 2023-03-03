package com.example.quick_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.reposImpl.NoteRepoImp
import com.example.local.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickNoteVM @Inject constructor(
    private val repo: NoteRepoImp
) : ViewModel() {
    fun addQuickNote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) {
            repo.addNote(note)
        }
}