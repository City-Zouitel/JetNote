package city.zouitel.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStoreRepoImpl(
    private val dataStore: DataStore<Preferences>
): DataStoreRepo {

    override val getLayout: Flow<String>
        get() = dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { it[Keys.LAYOUT_KEY] ?: Constants.GRID }

    override val getOrdination: Flow<String>
        get() = dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { it[Keys.ORDINATION_KEY] ?: Constants.DEFAULT_ORDER }

    override val getTheme: Flow<String>
        get() = dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { it[Keys.THEME_KEY] ?: Constants.DARK }

    override val isMute: Flow<Boolean>
        get() = dataStore.data
            .map { it[Keys.MUTE_KEY] ?: true }

    override val isLockMode: Flow<Boolean>
        get() = dataStore.data
            .map { it[Keys.LOCK_MODE_KEY] ?: false }

    override val isScreenshotBlock: Flow<Boolean>
        get() = dataStore.data
            .map { it[Keys.SCREENSHOT_BLOCK_KEY] ?: false }

    override val getGeminiApiKey: Flow<String?>
        get() = dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { it[Keys.GEMINI_API_KEY] }

    override suspend fun setLayout(layout: String) {
        dataStore.edit { it[Keys.LAYOUT_KEY] = layout }
    }

    override suspend fun setOrdination(order: String) {
        dataStore.edit { it[Keys.ORDINATION_KEY] = order }
    }

    override suspend fun setTheme(theme: String) {
        dataStore.edit { it[Keys.THEME_KEY] = theme }
    }

    override suspend fun setMute(isMute: Boolean) {
        dataStore.edit { it[Keys.MUTE_KEY] = isMute }
    }

    override suspend fun setLockMode(isLocked: Boolean) {
        dataStore.edit { it[Keys.LOCK_MODE_KEY] = isLocked }
    }

    override suspend fun setScreenshotBlock(isBlocked: Boolean) {
        dataStore.edit { it[Keys.SCREENSHOT_BLOCK_KEY] = isBlocked }
    }

    override suspend fun setGeminiApiKey(apiKey: String) {
        dataStore.edit { preferences -> preferences[Keys.GEMINI_API_KEY] = apiKey }
    }
}