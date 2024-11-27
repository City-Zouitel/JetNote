package city.zouitel.assistant.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import city.zouitel.assistant.mapper.AssistantMapper
import city.zouitel.assistant.ui.AssistantScreenModel

val assistantKoinModule = module {
    factoryOf(::AssistantMapper)
    factoryOf(::AssistantScreenModel)
}