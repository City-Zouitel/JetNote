package city.zouitel.generativeai.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.room.Room
import city.zouitel.generativeai.Cache
import city.zouitel.generativeai.Constants.GEMINI_MODEL
import city.zouitel.generativeai.Keys
import city.zouitel.generativeai.datasouceImpl.MessageDataSourceImpl
import city.zouitel.generativeai.mapper.MessageMapper
import city.zouitel.networkRepo.datasource.MessageDataSource
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val generativeKoinModule = module {

    singleOf(::MessageDataSourceImpl) bind MessageDataSource::class
    factoryOf(::MessageMapper)

    single { get<Cache>().getCacheDao() }

    single {
        Room.inMemoryDatabaseBuilder(androidContext(), Cache::class.java).build()
    }

    single { param ->
        var _apiKey: String? = null
        param.insert(
            index = 0,
            value = CoroutineScope(Dispatchers.IO)
        )
        param.insert(
            index = 1,
            value = get<DataStore<Preferences>>().data
                .catch { emit(emptyPreferences()) }
                .map { it[Keys.GEMINI_API_KEY] }
        )

        param.component1<CoroutineScope>().launch {
            param.component2<Flow<String>>().collect { key ->
                _apiKey = key
            }
        }
        GenerativeModel(
            modelName = GEMINI_MODEL,
            apiKey = _apiKey ?: ""
        )
    }
}