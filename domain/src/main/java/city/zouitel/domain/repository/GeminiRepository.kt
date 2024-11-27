package city.zouitel.domain.repository

import city.zouitel.domain.model.GeminiQuest
import kotlinx.coroutines.flow.Flow

interface GeminiRepository {
    suspend fun sendGeminiPrompt(geminiQuest: GeminiQuest): Flow<String>
}