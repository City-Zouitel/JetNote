package city.zouitel.repository.di

import city.zouitel.domain.repository.GeminiRepository
import city.zouitel.domain.usecase.GeminiUseCase
import city.zouitel.repository.mapper.GeminiMapper
import city.zouitel.repository.repositoryImpl.GeminiRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkRepositoryKoinModule = module {
    factoryOf(::GeminiMapper)
    singleOf(::GeminiRepositoryImpl) bind GeminiRepository::class

    factoryOf(GeminiUseCase::SendGeminiPrompt)
}