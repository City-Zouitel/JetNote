package city.zouitel.assistant.di

import cafe.adriel.voyager.core.registry.screenModule
import city.zouitel.assistant.connection.Connection
import city.zouitel.assistant.connection.ConnectionImpl
import city.zouitel.assistant.mapper.AssistantMapper
import city.zouitel.assistant.ui.AssistantScreen
import city.zouitel.assistant.ui.AssistantScreenModel
import city.zouitel.domain.provider.SharedScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val assistantKoinModule = module {
    factoryOf(::AssistantMapper)
    factoryOf(::AssistantScreenModel)
    singleOf(::ConnectionImpl) bind Connection::class

    single {
        ConnectionImpl(androidContext())
    }
}

val assistantScreenModule = screenModule {
    register<SharedScreen.Assistant> {
        AssistantScreen()
    }
}