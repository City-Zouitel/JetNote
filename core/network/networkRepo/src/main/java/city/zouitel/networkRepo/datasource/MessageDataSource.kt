package city.zouitel.networkRepo.datasource

import city.zouitel.networkRepo.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {

    val observeAllMessages: Flow<List<Message>>

    suspend fun insertMessage(message: Message)
}