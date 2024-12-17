package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.repository.MessageRepository
import city.zouitel.repository.datasource.MessageDataSource
import city.zouitel.repository.mapper.MessageMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.Message as OutMessage

class GeminiRepositoryImpl(
    private val dataSource: MessageDataSource,
    private val mapper: MessageMapper
): MessageRepository {

    override val observeAll: Flow<List<OutMessage>>
        get() = dataSource.observeAllMessages.map { mapper.toDomain(it) }

    override suspend fun insert(message: OutMessage) {
        dataSource.insertMessage(mapper.fromDomain(message))
    }
}