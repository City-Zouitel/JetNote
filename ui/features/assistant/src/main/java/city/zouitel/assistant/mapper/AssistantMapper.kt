package city.zouitel.assistant.mapper

import city.zouitel.assistant.model.GeminiQuest as InGeminiQuest
import city.zouitel.domain.model.GeminiQuest as OutGeminiQuest

class AssistantMapper {

    fun toDomain(geminiQuest: InGeminiQuest) = OutGeminiQuest(
        image = geminiQuest.image,
        prompt = geminiQuest.prompt
    )
}