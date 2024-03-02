package city.zouitel.tags.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.TagUseCase
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tags.model.Tag as InTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TagScreenModel(
    getAllTags: TagUseCase.GetAllTags,
    private val add: TagUseCase.AddTag,
    private val update: TagUseCase.UpdateTag,
    private val delete: TagUseCase.DeleteTag,
    private val mapper: TagMapper
): ScreenModel {

    private val _getAllTags = MutableStateFlow<List<InTag>>(emptyList())

    val getAllLTags: StateFlow<List<InTag>>
        get() = _getAllTags
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        screenModelScope.launch(Dispatchers.IO) {
            getAllTags.invoke().collect { list ->
                _getAllTags.value = list.map { tag -> mapper.toView(tag) }
            }
        }
    }

    fun addTag(tag: InTag) = screenModelScope.launch(Dispatchers.IO) {
        add.invoke(mapper.toDomain(tag))
    }

    fun updateTag(tag: InTag) = screenModelScope.launch(Dispatchers.IO) {
        update.invoke(mapper.toDomain(tag))
    }

    fun deleteTag(tag: InTag) = screenModelScope.launch(Dispatchers.IO) {
        delete.invoke(mapper.toDomain(tag))
    }
}