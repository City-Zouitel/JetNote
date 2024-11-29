package city.zouitel.generativeai.datasouceImpl

import city.zouitel.generativeai.mapper.GeminiMapper
import city.zouitel.repository.datasource.GeminiDataSource
import city.zouitel.repository.model.GeminiQuest
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GeminiDataSourceImpl(
    private val generativeModel: GenerativeModel,
    private val mapper: GeminiMapper
): GeminiDataSource {

    override suspend fun sendGeminiPrompt(geminiQuest: GeminiQuest): Flow<String> {
        var response = flow { emit("") }
            runCatching {
                generativeModel.generateContent(
                    content {
                        mapper.fromRepo(geminiQuest).image?.let { image(it) }
                        text(mapper.fromRepo(geminiQuest).prompt)
                    }
                ).run {
                    text?.let {
                        response = flow { emit(it) }
                    } ?: run {
                        response = flow { emit("null") }
                    }
                }
            }.onFailure {
                response = flow { emit(it.message.toString()) }
            }
        return mapper.toRepo(response)
    }
}