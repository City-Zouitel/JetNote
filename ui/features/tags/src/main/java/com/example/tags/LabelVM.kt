package com.example.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.reposImpl.LabelRepoImpl
import com.example.local.model.TagEntity
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
    private val repo: LabelRepoImpl
): ViewModel(){

    private val _getAllLabels = MutableStateFlow<List<TagEntity>>(emptyList())
    val getAllLabels: StateFlow<List<TagEntity>>
    get() = _getAllLabels.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllLabels.collect {
                _getAllLabels.value = it
            }
        }
    }

    fun addLabel(tagEntity: TagEntity) = viewModelScope.launch(Dispatchers.IO) {
            repo.addLabel(tagEntity)
        }

    fun updateLabel(tagEntity: TagEntity) = viewModelScope.launch(Dispatchers.IO) {
            repo.updateLabel(tagEntity)
        }

    fun deleteLabel(tagEntity: TagEntity) = viewModelScope.launch(Dispatchers.IO) {
            repo.deleteLabel(tagEntity)
        }

}