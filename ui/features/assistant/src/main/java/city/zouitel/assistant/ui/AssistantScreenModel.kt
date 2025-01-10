package city.zouitel.assistant.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.assistant.mapper.AssistantMapper
import city.zouitel.assistant.model.Message
import city.zouitel.domain.usecase.MessageUseCase
import city.zouitel.logic.withFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AssistantScreenModel(
    private val observeAll: MessageUseCase.ObserveAllMessages,
    private val insert: MessageUseCase.InsertMessage,
    private val mapper: AssistantMapper
): ScreenModel {

    private val _observeAllMessages: MutableStateFlow<List<Message>> =
        MutableStateFlow(emptyList())

    val observeAllMessages: StateFlow<List<Message>> =
        _observeAllMessages.withFlow(emptyList())

    init {
        screenModelScope.launch(Dispatchers.IO) {
            observeAll().collect { messages ->
                _observeAllMessages.value = mapper.fromDomain(messages)
            }
        }
    }

    fun insertMessage(message: Message) {
        screenModelScope.launch(Dispatchers.IO) {
            insert(mapper.toDomain(message))
        }
    }
}