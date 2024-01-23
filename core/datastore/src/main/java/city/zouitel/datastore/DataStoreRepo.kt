package city.zouitel.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    val getLayout: Flow<String>

    val getOrdination: Flow<String>

    val getTheme: Flow<String>

    val getSound: Flow<Boolean>

    suspend fun setLayout(layout: String)

    suspend fun setOrdination(order: String)

    suspend fun setTheme(theme: String)

    suspend fun setSound(sound: Boolean)
}