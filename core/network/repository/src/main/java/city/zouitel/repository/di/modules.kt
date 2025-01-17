package city.zouitel.repository.di

import city.zouitel.domain.repository.MessageRepository
import city.zouitel.domain.usecase.MessageUseCase
import city.zouitel.repository.mapper.MessageMapper
import city.zouitel.repository.repositoryImpl.GeminiRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkRepositoryKoinModule = module {
    factoryOf(::MessageMapper)
    singleOf(::GeminiRepositoryImpl) bind MessageRepository::class

    factoryOf(MessageUseCase::ObserveAllMessages)
    factoryOf(MessageUseCase::InsertMessage)
}