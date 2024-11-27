package city.zouitel.domain.usecase

import city.zouitel.domain.model.GeminiQuest
import city.zouitel.domain.repository.GeminiRepository
import kotlinx.coroutines.flow.Flow

sealed class GeminiUseCase {
    data class SendGeminiPrompt(
        private val repository: GeminiRepository
    ): GeminiUseCase() {
        suspend operator fun invoke(geminiQuest: GeminiQuest): Flow<String> = repository.sendGeminiPrompt(geminiQuest)
    }
}