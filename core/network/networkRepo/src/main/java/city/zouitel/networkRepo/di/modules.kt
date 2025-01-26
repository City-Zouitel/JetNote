package city.zouitel.networkRepo.di

import city.zouitel.domain.repository.MessageRepository
import city.zouitel.domain.usecase.MessageUseCase
import city.zouitel.networkRepo.mapper.MessageMapper
import city.zouitel.networkRepo.repositoryImpl.GeminiRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkRepoDIModule = module {
    factoryOf(::MessageMapper)
    singleOf(::GeminiRepositoryImpl) bind MessageRepository::class

    factoryOf(MessageUseCase::ObserveAllMessages)
    factoryOf(MessageUseCase::InsertMessage)
}