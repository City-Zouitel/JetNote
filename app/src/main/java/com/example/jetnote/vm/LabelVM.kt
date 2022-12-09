package com.example.jetnote.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnote.db.entities.label.Label
import com.example.jetnote.reposImp.LabelRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelVM @Inject constructor(
    private val repo: LabelRepoImp
): ViewModel(){

    private val _getAllLabels = MutableStateFlow<List<Label>>(emptyList())
    val getAllLabels: StateFlow<List<Label>>
    get() = _getAllLabels.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllLabels.collect {
                _getAllLabels.value = it
            }
        }
    }

    fun addLabel(label: Label) = viewModelScope.launch(Dispatchers.IO) {
            repo.addLabel(label)
        }

    fun updateLabel(label: Label) = viewModelScope.launch(Dispatchers.IO) {
            repo.updateLabel(label)
        }

    fun deleteLabel(label: Label) = viewModelScope.launch(Dispatchers.IO) {
            repo.deleteLabel(label)
        }

}