package com.example.tags.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.TagUseCase
import com.example.tags.mapper.TagMapper
import com.example.tags.model.NoteAndTag
import com.example.tags.model.Tag as InTag
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
    getAllTags: TagUseCase.GetAllTags,
    private val add: TagUseCase.AddTag,
    private val update: TagUseCase.UpdateTag,
    private val delete: TagUseCase.DeleteTag,
    private val mapper: TagMapper
): ViewModel(){

    private val _getAllTags = MutableStateFlow<List<InTag>>(emptyList())

    val getAllLTags: StateFlow<List<InTag>>
        get() = _getAllTags
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTags.invoke().collect { list ->
                _getAllTags.value = list.map { tag -> mapper.toView(tag) }
            }
        }
    }

    fun addTag(tag: InTag) = viewModelScope.launch(Dispatchers.IO) {
        add.invoke(mapper.toDomain(tag))
    }

    fun updateTag(tag: InTag) = viewModelScope.launch(Dispatchers.IO) {
        update.invoke(mapper.toDomain(tag))
    }

    fun deleteTag(tag: InTag) = viewModelScope.launch(Dispatchers.IO) {
        delete.invoke(mapper.toDomain(tag))
    }
}