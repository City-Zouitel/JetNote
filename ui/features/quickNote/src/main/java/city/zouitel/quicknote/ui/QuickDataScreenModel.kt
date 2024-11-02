package city.zouitel.quicknote.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.quicknote.mapper.QuickDataMapper
import city.zouitel.quicknote.model.QuickData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuickDataScreenModel(
    private val add: DataUseCase.AddData,
    private val mapper: QuickDataMapper
): ScreenModel, UiEventHandler<QuickData> {

    override fun sendUiEvent(event: UiEvent<QuickData>) {
        when(event) {
            is UiEvent.Insert -> performUiEvent { add(mapper.toDomain(event.data)) }
            else -> throw Exception("Not Implemented!")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}