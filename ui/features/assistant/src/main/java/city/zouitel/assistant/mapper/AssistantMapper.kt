package city.zouitel.assistant.mapper

import kotlinx.coroutines.flow.Flow
import city.zouitel.assistant.model.GeminiQuest as InGeminiQuest
import city.zouitel.domain.model.GeminiQuest as OutGeminiQuest

class AssistantMapper {
    fun fromDomain(response: Flow<String>) = response


    fun toDomain(geminiQuest: InGeminiQuest) = OutGeminiQuest(
        image = geminiQuest.image,
        prompt = geminiQuest.prompt
    )
}