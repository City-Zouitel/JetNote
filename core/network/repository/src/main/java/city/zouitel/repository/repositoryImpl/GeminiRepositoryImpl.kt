package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.GeminiQuest
import city.zouitel.domain.repository.GeminiRepository
import city.zouitel.repository.datasource.GeminiDataSource
import city.zouitel.repository.mapper.GeminiMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GeminiRepositoryImpl(
    private val dataSource: GeminiDataSource,
    private val mapper: GeminiMapper
): GeminiRepository {

    override suspend fun sendGeminiPrompt(geminiQuest: GeminiQuest): Flow<String> {
        val response = flow { emit("...") }
        dataSource.sendGeminiPrompt(mapper.fromDomain(geminiQuest))
        return mapper.toDomain(response)
    }
}