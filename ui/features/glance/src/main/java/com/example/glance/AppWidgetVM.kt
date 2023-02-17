package com.example.glance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.reposImpl.EntityRepoImp
import com.example.local.model.Entity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppWidgetVM @Inject constructor(
    private val repo: EntityRepoImp
): ViewModel() {
    private val _allNotesById = MutableStateFlow<List<Entity>>(emptyList())
    val allNotesById: StateFlow<List<Entity>>
        get() = _allNotesById.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = listOf()
        )
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllNotesById.collect {
                _allNotesById.value = it
            }
        }
    }
}