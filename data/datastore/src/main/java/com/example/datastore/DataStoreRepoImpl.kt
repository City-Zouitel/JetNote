package com.example.datastore

import kotlinx.coroutines.flow.Flow
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreRepoImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepo {
    override val getLayout: Flow<String>
        get() = dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it[Keys.LAYOUT_KEY] ?: Cons.GRID
            }

    override suspend fun setLayout(layout: String) {
        dataStore.edit {
            it[Keys.LAYOUT_KEY] = layout
        }

    }
}