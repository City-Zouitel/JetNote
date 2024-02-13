package city.zouitel.tags.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.domain.usecase.TagUseCase
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tags.model.Tag as InTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TagViewModel(
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