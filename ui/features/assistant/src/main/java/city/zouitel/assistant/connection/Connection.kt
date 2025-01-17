package city.zouitel.assistant.connection

import kotlinx.coroutines.flow.Flow

interface Connection {
    val isOnline: Flow<Boolean>
}