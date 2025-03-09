package city.zouitel.repository.di

import city.zouitel.domain.repository.AudioRepo
import city.zouitel.domain.repository.DataRepository
import city.zouitel.domain.repository.LinkRepository
import city.zouitel.domain.repository.MediaRepository
import city.zouitel.domain.repository.NoteAndTagRepository
import city.zouitel.domain.repository.NoteRepository
import city.zouitel.domain.repository.RecordRepo
import city.zouitel.domain.repository.ReminderRepo
import city.zouitel.domain.repository.RootRepository
import city.zouitel.domain.repository.TagRepository
import city.zouitel.domain.repository.TaskRepository
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.domain.usecase.MediaUseCase
import city.zouitel.domain.usecase.NoteAndTagUseCase
import city.zouitel.domain.usecase.NoteUseCase
import city.zouitel.domain.usecase.RecordUseCase
import city.zouitel.domain.usecase.ReminderUseCase
import city.zouitel.domain.usecase.RootUseCase
import city.zouitel.domain.usecase.TagUseCase
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.repository.mapper.AudioMapper
import city.zouitel.repository.mapper.DataMapper
import city.zouitel.repository.mapper.LinkMapper
import city.zouitel.repository.mapper.MediaMapper
import city.zouitel.repository.mapper.NoteAndTagMapper
import city.zouitel.repository.mapper.NoteMapper
import city.zouitel.repository.mapper.RecordMapper
import city.zouitel.repository.mapper.ReminderMapper
import city.zouitel.repository.mapper.RootMapper
import city.zouitel.repository.mapper.TagMapper
import city.zouitel.repository.mapper.TaskMapper
import city.zouitel.repository.repositoryImpl.AudioRepoImpl
import city.zouitel.repository.repositoryImpl.DataRepositoryImpl
import city.zouitel.repository.repositoryImpl.LinkRepositoryImpl
import city.zouitel.repository.repositoryImpl.MediaRepoImpl
import city.zouitel.repository.repositoryImpl.NoteAndTagRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteRepositoryImpl
import city.zouitel.repository.repositoryImpl.RecordRepoImpl
import city.zouitel.repository.repositoryImpl.ReminderRepoImpl
import city.zouitel.repository.repositoryImpl.RootRepositoryImpl
import city.zouitel.repository.repositoryImpl.TagRepositoryImpl
import city.zouitel.repository.repositoryImpl.TaskRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val localRepoDIModule = module {

    //Mappers.
    factoryOf(::DataMapper)
    factoryOf(::LinkMapper)
    factoryOf(::TagMapper)
    factoryOf(::TaskMapper)
    factoryOf(::AudioMapper)
    factoryOf(::NoteAndTagMapper)
    factoryOf(::RootMapper)
    factoryOf(::MediaMapper)
    factoryOf(::ReminderMapper)
    factoryOf(::RecordMapper)
    factory {
        NoteMapper(get(), get())
    }

    //Repositories.
    singleOf(::AudioRepoImpl) bind AudioRepo::class
    singleOf(::DataRepositoryImpl) bind DataRepository::class
    singleOf(::LinkRepositoryImpl) bind LinkRepository::class
    singleOf(::MediaRepoImpl) bind MediaRepository::class
    singleOf(::NoteAndTagRepositoryImpl) bind NoteAndTagRepository::class
    singleOf(::NoteRepositoryImpl) bind NoteRepository::class
    singleOf(::ReminderRepoImpl) bind ReminderRepo::class
    singleOf(::RootRepositoryImpl) bind RootRepository::class
    singleOf(::TagRepositoryImpl) bind TagRepository::class
    singleOf(::TaskRepositoryImpl) bind TaskRepository::class
    singleOf(::RecordRepoImpl) bind RecordRepo::class

    //UseCases.
    factoryOf(DataUseCase::Insert)
    factoryOf(DataUseCase::Archive)
    factoryOf(DataUseCase::Rollback)
    factoryOf(DataUseCase::Delete)
    factoryOf(DataUseCase::Erase)

    factoryOf(LinkUseCase::ObserveAll)
    factoryOf(LinkUseCase::ObserveByUid)
    factoryOf(LinkUseCase::Insert)
    factoryOf(LinkUseCase::DeleteById)
    factoryOf(LinkUseCase::DeleteByUid)

    factoryOf(NoteAndTagUseCase::ObserveAll)
    factoryOf(NoteAndTagUseCase::Insert)
    factoryOf(NoteAndTagUseCase::DeleteByUid)
    factoryOf(NoteAndTagUseCase::DeleteById)
    factoryOf(NoteAndTagUseCase::DeleteDrafts)

    factoryOf(NoteUseCase::ObserveByDefault)
    factoryOf(NoteUseCase::ObserveByName)
    factoryOf(NoteUseCase::ObserveByOldest)
    factoryOf(NoteUseCase::ObserveByNewest)
    factoryOf(NoteUseCase::ObserveArchives)
    factoryOf(NoteUseCase::ObserveByPriority)

    factoryOf(TagUseCase::ObserveAll)
    factoryOf(TagUseCase::Insert)
    factoryOf(TagUseCase::DeleteById)

    factoryOf(TaskUseCase::ObserveAll)
    factoryOf(TaskUseCase::ObserveByUid)
    factoryOf(TaskUseCase::Insert)
    factoryOf(TaskUseCase::DeleteById)
    factoryOf(TaskUseCase::DeleteByUid)
    factoryOf(TaskUseCase::DeleteDrafts)

    factoryOf(RootUseCase::RootUseCase)

    factoryOf(AudioUseCase::ObserveAll)
    factoryOf(AudioUseCase::ObserveByUid)
    factoryOf(AudioUseCase::Insert)
    factoryOf(AudioUseCase::DeleteByUid)
    factoryOf(AudioUseCase::DeleteById)

    factoryOf(MediaUseCase::ObserveAll)
    factoryOf(MediaUseCase::ObserveByUid)
    factoryOf(MediaUseCase::Insert)
    factoryOf(MediaUseCase::DeleteById)

    factoryOf(MediaUseCase::GetDrafts)
    factoryOf(MediaUseCase::DeleteDrafts)

    factoryOf(ReminderUseCase::ObserveByUid)
    factoryOf(ReminderUseCase::Insert)
    factoryOf(ReminderUseCase::UpdateById)
    factoryOf(ReminderUseCase::DeleteById)
    factoryOf(ReminderUseCase::DeleteByUid)
    factoryOf(ReminderUseCase::DeleteDrafts)

    factoryOf(RecordUseCase::ObserveAll)
    factoryOf(RecordUseCase::ObserveByUid)
    factoryOf(RecordUseCase::Insert)
    factoryOf(RecordUseCase::Delete)
}