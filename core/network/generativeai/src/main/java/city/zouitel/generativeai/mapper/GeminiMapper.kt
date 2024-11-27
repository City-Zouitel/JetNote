package city.zouitel.generativeai.mapper

import kotlinx.coroutines.flow.Flow
import city.zouitel.generativeai.model.GeminiQuest as InGeminiQuest
import city.zouitel.repository.model.GeminiQuest as OutGeminiQuest

class GeminiMapper {

    fun toRepo(response: Flow<String>) = response

    fun fromRepo(geminiQuest: OutGeminiQuest) = InGeminiQuest(
        image = geminiQuest.image,
        prompt = geminiQuest.prompt
    )
}