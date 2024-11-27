package city.zouitel.assistant.ui

import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.domain.usecase.GeminiUseCase

class AssistantScreenModel(
    private val sendGeminiPrompt: GeminiUseCase.SendGeminiPrompt,
): ScreenModel {

    fun sendPrompt() {

    }
}