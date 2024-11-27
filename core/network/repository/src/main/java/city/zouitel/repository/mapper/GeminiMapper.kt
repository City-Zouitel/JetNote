package city.zouitel.repository.mapper

import kotlinx.coroutines.flow.Flow
import city.zouitel.domain.model.GeminiQuest as OutGeminiQuest
import city.zouitel.repository.model.GeminiQuest as InGeminiQuest

class GeminiMapper {
    fun toDomain(response: Flow<String>) = response


    fun fromDomain(geminiQuest: OutGeminiQuest) = InGeminiQuest(
        image = geminiQuest.image,
        prompt = geminiQuest.prompt
    )
}