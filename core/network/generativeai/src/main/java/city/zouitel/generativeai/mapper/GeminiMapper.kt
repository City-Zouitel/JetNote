package city.zouitel.generativeai.mapper

import city.zouitel.domain.utils.RequestState
import kotlinx.coroutines.flow.Flow
import city.zouitel.generativeai.model.GeminiQuest as InGeminiQuest
import city.zouitel.repository.model.GeminiQuest as OutGeminiQuest

class GeminiMapper {

    fun fromRepo(geminiQuest: OutGeminiQuest) = InGeminiQuest(
        image = geminiQuest.image,
        prompt = geminiQuest.prompt
    )
}