package city.zouitel.repository.datasource

import city.zouitel.repository.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {

    val observeAllMessages: Flow<List<Message>>

    suspend fun insertMessage(message: Message)
}