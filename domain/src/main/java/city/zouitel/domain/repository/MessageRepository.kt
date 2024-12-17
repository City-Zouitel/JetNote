package city.zouitel.domain.repository

import city.zouitel.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    val observeAll: Flow<List<Message>>

    suspend fun insert(message: Message)
}