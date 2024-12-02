package city.zouitel.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    val getLayout: Flow<String>

    val getOrdination: Flow<String>

    val getTheme: Flow<String>

    val isMute: Flow<Boolean>

    val isLockMode: Flow<Boolean>

    val isScreenshotBlock: Flow<Boolean>

    suspend fun setLayout(layout: String)

    suspend fun setOrdination(order: String)

    suspend fun setTheme(theme: String)

    suspend fun setMute(isMute: Boolean)

    suspend fun setLockMode(isLocked: Boolean)

    suspend fun setScreenshotBlock(isBlocked: Boolean)

    suspend fun setGeminiApiKey(apiKey: String)
}