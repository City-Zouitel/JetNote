package city.zouitel.generativeai.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import city.zouitel.generativeai.Constants.GEMINI_MODEL
import city.zouitel.generativeai.Keys
import city.zouitel.generativeai.datasouceImpl.GeminiDataSourceImpl
import city.zouitel.generativeai.mapper.GeminiMapper
import city.zouitel.repository.datasource.GeminiDataSource
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val generativeKoinModule = module {

    singleOf(::GeminiDataSourceImpl) bind GeminiDataSource::class
    factoryOf(::GeminiMapper)

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
                .map { it[Keys.GEMINI_API_KEY] ?: "" }
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