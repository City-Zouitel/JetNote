package com.example.quick_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.model.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickNoteVM @Inject constructor(

) : ViewModel() {
    fun addQuickNote(data: Data) = viewModelScope.launch(Dispatchers.IO) {

        }
}