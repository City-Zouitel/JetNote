package com.example.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.TagUseCase
import com.example.tags.mapper.TagMapper
import com.example.tags.model.Tag as InTag
import com.example.domain.model.Tag as OutTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val getAllTags: TagUseCase.GetAllTags,
    private val addTag: TagUseCase.AddTag,
    private val updateTag: TagUseCase.UpdateTag,
    private val deleteTag: TagUseCase.DeleteTag,
    private val mapper: TagMapper
): ViewModel(){

    private val _getAllLabels = MutableStateFlow<List<OutTag>>(emptyList())
    val getAllLabels: StateFlow<List<OutTag>>
    get() = _getAllLabels.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTags.invoke().collect {
                _getAllLabels.value.map { tag ->
                    mapper.toView(tag)
                }
            }
        }
    }

    fun addLabel(tag: InTag) = viewModelScope.launch(Dispatchers.IO) {
        addTag.invoke(mapper.toDomain(tag))
    }

    fun updateLabel(tag: InTag) = viewModelScope.launch(Dispatchers.IO) {
        updateTag.invoke(mapper.toDomain(tag))
    }

    fun deleteLabel(tag: InTag) = viewModelScope.launch(Dispatchers.IO) {
        deleteTag.invoke(mapper.toDomain(tag))
    }
}