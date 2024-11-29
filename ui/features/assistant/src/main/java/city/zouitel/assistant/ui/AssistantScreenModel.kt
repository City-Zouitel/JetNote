package city.zouitel.assistant.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.assistant.mapper.AssistantMapper
import city.zouitel.assistant.model.GeminiQuest
import city.zouitel.domain.usecase.GeminiUseCase
import city.zouitel.logic.asLogicFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AssistantScreenModel(
    private val sendGeminiPrompt: GeminiUseCase.SendGeminiPrompt,
    private val mapper: AssistantMapper
): ScreenModel {

    private val _getGenerativeResponse = MutableStateFlow("")
    val getGenerativeResponse: StateFlow<String> = _getGenerativeResponse.asLogicFlow("")

    fun sendPrompt(geminiQuest: GeminiQuest) {
        screenModelScope.launch(Dispatchers.IO) {
            sendGeminiPrompt(mapper.toDomain(geminiQuest)).collect {
                _getGenerativeResponse.value = it
            }
        }
    }
}