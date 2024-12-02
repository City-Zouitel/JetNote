package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.GeminiQuest
import city.zouitel.domain.repository.GeminiRepository
import city.zouitel.domain.utils.RequestState
import city.zouitel.repository.datasource.GeminiDataSource
import city.zouitel.repository.mapper.GeminiMapper
import kotlinx.coroutines.flow.Flow

class GeminiRepositoryImpl(
    private val dataSource: GeminiDataSource,
    private val mapper: GeminiMapper
): GeminiRepository {

    override suspend fun sendGeminiPrompt(geminiQuest: GeminiQuest): Flow<RequestState<String>> {
        return dataSource.sendGeminiPrompt(mapper.fromDomain(geminiQuest))
    }
}