package city.zouitel.repository.di

import city.zouitel.domain.repository.AudioRepo
import city.zouitel.domain.repository.DataRepository
import city.zouitel.domain.repository.LinkRepository
import city.zouitel.domain.repository.MediaRepository
import city.zouitel.domain.repository.NoteAndAudioRepository
import city.zouitel.domain.repository.NoteAndTagRepository
import city.zouitel.domain.repository.NoteRepository
import city.zouitel.domain.repository.ReminderRepo
import city.zouitel.domain.repository.RootRepository
import city.zouitel.domain.repository.TagRepository
import city.zouitel.domain.repository.TaskRepository
import city.zouitel.domain.repository.WidgetRepository
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.domain.usecase.MediaUseCase
import city.zouitel.domain.usecase.NoteAndAudioUseCase
import city.zouitel.domain.usecase.NoteAndTagUseCase
import city.zouitel.domain.usecase.NoteUseCase
import city.zouitel.domain.usecase.ReminderUseCase
import city.zouitel.domain.usecase.RootUseCase
import city.zouitel.domain.usecase.TagUseCase
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.domain.usecase.WidgetUseCase
import city.zouitel.repository.mapper.AudioMapper
import city.zouitel.repository.mapper.DataMapper
import city.zouitel.repository.mapper.LinkMapper
import city.zouitel.repository.mapper.MediaMapper
import city.zouitel.repository.mapper.NoteAndAudioMapper
import city.zouitel.repository.mapper.NoteAndTagMapper
import city.zouitel.repository.mapper.NoteMapper
import city.zouitel.repository.mapper.ReminderMapper
import city.zouitel.repository.mapper.RootMapper
import city.zouitel.repository.mapper.TagMapper
import city.zouitel.repository.mapper.TaskMapper
import city.zouitel.repository.mapper.WidgetMapper
import city.zouitel.repository.repositoryImpl.AudioRepoImpl
import city.zouitel.repository.repositoryImpl.DataRepositoryImpl
import city.zouitel.repository.repositoryImpl.LinkRepositoryImpl
import city.zouitel.repository.repositoryImpl.MediaRepoImpl
import city.zouitel.repository.repositoryImpl.NoteAndAudioRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteAndTagRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteRepositoryImpl
import city.zouitel.repository.repositoryImpl.ReminderRepoImpl
import city.zouitel.repository.repositoryImpl.RootRepositoryImpl
import city.zouitel.repository.repositoryImpl.TagRepositoryImpl
import city.zouitel.repository.repositoryImpl.TaskRepositoryImpl
import city.zouitel.repository.repositoryImpl.WidgetRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val localRepositoryKoinModule = module {

    //Mappers.
    factoryOf(::DataMapper)
    factoryOf(::LinkMapper)
    factoryOf(::TagMapper)
    factoryOf(::TaskMapper)
    factoryOf(::AudioMapper)
    factoryOf(::NoteAndTagMapper)
    factoryOf(::NoteAndAudioMapper)
    factoryOf(::RootMapper)
    factoryOf(::MediaMapper)
    factoryOf(::ReminderMapper)
    factory {
        NoteMapper(get(), get(), get())
    }
    factory {
        WidgetMapper(get(), get(), get())
    }

    //Repositories.
    singleOf(::DataRepositoryImpl) bind DataRepository::class
    singleOf(::NoteRepositoryImpl) bind NoteRepository::class
    singleOf(::LinkRepositoryImpl) bind LinkRepository::class
    singleOf(::TagRepositoryImpl) bind TagRepository::class
    singleOf(::NoteAndTagRepositoryImpl) bind NoteAndTagRepository::class
    singleOf(::TaskRepositoryImpl) bind TaskRepository::class
    singleOf(::AudioRepoImpl) bind AudioRepo::class
    singleOf(::NoteAndAudioRepositoryImpl) bind NoteAndAudioRepository::class
    singleOf(::WidgetRepositoryImpl) bind WidgetRepository::class
    singleOf(::RootRepositoryImpl) bind RootRepository::class
    singleOf(::MediaRepoImpl) bind MediaRepository::class
    singleOf(::ReminderRepoImpl) bind ReminderRepo::class

    //UseCases.
    factoryOf(DataUseCase::AddData)
    factoryOf(DataUseCase::EditData)
    factoryOf(DataUseCase::DeleteData)
    factoryOf(DataUseCase::DeleteAllTrashedData)

    factoryOf(LinkUseCase::ObserveAll)
    factoryOf(LinkUseCase::ObserveByUid)
    factoryOf(LinkUseCase::Insert)
    factoryOf(LinkUseCase::DeleteById)
    factoryOf(LinkUseCase::DeleteByUid)

    factoryOf(NoteAndTagUseCase::AddNoteAndTag)
    factoryOf(NoteAndTagUseCase::DeleteNoteAndTag)
    factoryOf(NoteAndTagUseCase::GetAllNotesAndTags)

    factoryOf(NoteUseCase::GetAllNotesById)
    factoryOf(NoteUseCase::GetAllNotesByName)
    factoryOf(NoteUseCase::GetAllNotesByNewest)
    factoryOf(NoteUseCase::GetAllNotesByOldest)
    factoryOf(NoteUseCase::GetAllNotesByPriority)
    factoryOf(NoteUseCase::GetAllRemovedNotes)

    factoryOf(TagUseCase::AddTag)
    factoryOf(TagUseCase::DeleteTag)
    factoryOf(TagUseCase::UpdateTag)
    factoryOf(TagUseCase::GetAllTags)

    factoryOf(TaskUseCase::ObserveAll)
    factoryOf(TaskUseCase::ObserveByUid)
    factoryOf(TaskUseCase::Insert)
    factoryOf(TaskUseCase::DeleteById)
    factoryOf(TaskUseCase::DeleteByUid)

    factoryOf(WidgetUseCase::GetAllWidgetMainEntityById)

    factoryOf(RootUseCase::RootUseCase)

    factoryOf(AudioUseCase::GetAllAudios)
    factoryOf(AudioUseCase::AddAudio)
    factoryOf(AudioUseCase::UpdateAudio)
    factoryOf(AudioUseCase::DeleteAudio)

    factoryOf(NoteAndAudioUseCase::GetAllNotesAndAudios)
    factoryOf(NoteAndAudioUseCase::AddNoteAndAudio)
    factoryOf(NoteAndAudioUseCase::UpdateNoteAndAudio)
    factoryOf(NoteAndAudioUseCase::DeleteNoteAndAudio)

    factoryOf(MediaUseCase::ObserveAll)
    factoryOf(MediaUseCase::ObserveByUid)
    factoryOf(MediaUseCase::Insert)
    factoryOf(MediaUseCase::DeleteById)
    factoryOf(MediaUseCase::DeleteByUid)

    factoryOf(ReminderUseCase::ObserveByUid)
    factoryOf(ReminderUseCase::Insert)
    factoryOf(ReminderUseCase::UpdateById)
    factoryOf(ReminderUseCase::DeleteById)
    factoryOf(ReminderUseCase::DeleteByUid)
}