package city.zouitel.widget.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteUseCase
import city.zouitel.widget.mapper.WidgetMapper
import city.zouitel.widget.model.WidgetNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import city.zouitel.widget.model.WidgetNote as InNote

class WidgetScreenModel(
    private val observeByDefault: NoteUseCase.ObserveByDefault,
    private val mapper: WidgetMapper
): ScreenModel {

    // for observing the data changes.
    private val _allNotesById: MutableStateFlow<List<WidgetNote>> = MutableStateFlow(
        emptyList()
    )
    val allNotesById: StateFlow<List<InNote>> = _allNotesById
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    init {
        screenModelScope.launch(context = Dispatchers.IO) {
            observeByDefault.invoke().collect { notes -> _allNotesById.value = mapper.fromDomain(notes) }
        }
    }
}