package city.zouitel.domain.repository

import city.zouitel.domain.model.GeminiQuest
import city.zouitel.domain.utils.RequestState
import kotlinx.coroutines.flow.Flow

interface GeminiRepository {

    suspend fun sendGeminiPrompt(geminiQuest: GeminiQuest): Flow<RequestState<String>>
}