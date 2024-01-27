package city.zouitel.datastore

import kotlinx.coroutines.flow.Flow
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStoreRepoImpl(
    private val dataStore: DataStore<Preferences>
): DataStoreRepo {

    override val getLayout: Flow<String>
        get() = dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it[Keys.LAYOUT_KEY] ?: Cons.GRID
            }

    override val getOrdination: Flow<String>
        get() = dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it[Keys.ORDINATION_KEY] ?: Cons.ORDINATION
            }

    override val getTheme: Flow<String>
        get() = dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it[Keys.THEME_KEY] ?: Cons.DARK
            }

    override val getSound: Flow<Boolean>
        get() = dataStore.data
            .map {
                it[Keys.SOUND_KEY] ?: false
            }

    override suspend fun setLayout(layout: String) {
        dataStore.edit {
            it[Keys.LAYOUT_KEY] = layout
        }

    }

    override suspend fun setOrdination(order: String) {
        dataStore.edit {
            it[Keys.ORDINATION_KEY] = order
        }
    }

    override suspend fun setTheme(theme: String) {
        dataStore.edit {
            it[Keys.THEME_KEY] = theme
        }
    }

    override suspend fun setSound(sound: Boolean) {
        dataStore.edit {
            it[Keys.SOUND_KEY] = sound
        }
    }

}