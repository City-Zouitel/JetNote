package city.zouitel.generativeai.datasouceImpl

import city.zouitel.domain.utils.RequestState
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

    override suspend fun sendGeminiPrompt(geminiQuest: GeminiQuest): Flow<RequestState<String>> {
        val response = flow {
            emit(RequestState.Loading)
            runCatching {
                generativeModel.generateContent(
                    content {
                        mapper.fromRepo(geminiQuest).image?.let { image(it) }
                        text(mapper.fromRepo(geminiQuest).prompt)
                    }
                ).run {
                    text?.let {
                        emit(RequestState.Success(it))
                    } ?: run {
                        emit(RequestState.Error("No response from Gemini."))
                    }
                }
            }.onFailure {
                emit(RequestState.Error(it.message.toString()))
            }
        }
        return response
    }
}