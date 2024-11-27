package city.zouitel.repository.datasource

import city.zouitel.repository.model.GeminiQuest
import kotlinx.coroutines.flow.Flow

interface GeminiDataSource {
    suspend fun sendGeminiPrompt(geminiQuest: GeminiQuest): Flow<String>
}