package city.zouitel.repository.di

import city.zouitel.domain.repository.AudioRepository
import city.zouitel.domain.repository.DataRepository
import city.zouitel.domain.repository.LinkRepository
import city.zouitel.domain.repository.NoteAndAudioRepository
import city.zouitel.domain.repository.NoteAndLinkRepository
import city.zouitel.domain.repository.NoteAndTagRepository
import city.zouitel.domain.repository.NoteAndTaskRepository
import city.zouitel.domain.repository.NoteRepository
import city.zouitel.domain.repository.RootRepository
import city.zouitel.domain.repository.TagRepository
import city.zouitel.domain.repository.TaskRepository
import city.zouitel.domain.repository.WidgetRepository
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.domain.usecase.NoteAndAudioUseCase
import city.zouitel.domain.usecase.NoteAndLinkUseCase
import city.zouitel.domain.usecase.NoteAndTagUseCase
import city.zouitel.domain.usecase.NoteAndTaskUseCase
import city.zouitel.domain.usecase.NoteUseCase
import city.zouitel.domain.usecase.RootUseCase
import city.zouitel.domain.usecase.TagUseCase
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.domain.usecase.WidgetUseCase
import city.zouitel.repository.mapper.DataMapper
import city.zouitel.repository.mapper.LinkMapper
import city.zouitel.repository.mapper.NoteAndLinkMapper
import city.zouitel.repository.mapper.NoteAndTagMapper
import city.zouitel.repository.mapper.NoteAndTaskMapper
import city.zouitel.repository.mapper.WidgetMapper
import city.zouitel.repository.mapper.RootMapper
import city.zouitel.repository.mapper.TagMapper
import city.zouitel.repository.mapper.TaskMapper
import city.zouitel.repository.mapper.AudioMapper
import city.zouitel.repository.mapper.NoteAndAudioMapper
import city.zouitel.repository.repositoryImpl.DataRepositoryImpl
import city.zouitel.repository.repositoryImpl.LinkRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteAndLinkRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteAndTagRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteAndTaskRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteRepositoryImpl
import city.zouitel.repository.repositoryImpl.RootRepositoryImpl
import city.zouitel.repository.repositoryImpl.AudioRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteAndAudioRepositoryImpl
import city.zouitel.repository.repositoryImpl.TagRepositoryImpl
import city.zouitel.repository.repositoryImpl.TaskRepositoryImpl
import city.zouitel.repository.repositoryImpl.WidgetRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryKoinModule = module {

    //Mappers.
    factoryOf(::DataMapper)
    factoryOf(::LinkMapper)
    factoryOf(::TagMapper)
    factoryOf(::TaskMapper)
    factoryOf(::AudioMapper)
    factoryOf(::NoteAndLinkMapper)
    factoryOf(::NoteAndTagMapper)
    factoryOf(::NoteAndTaskMapper)
    factoryOf(::NoteAndAudioMapper)
    factoryOf(::RootMapper)
    factory {
        WidgetMapper(get(), get(), get(), get())
    }
    factory {
        WidgetMapper(get(), get(), get(), get())
    }

    //Repositories.
    singleOf(::DataRepositoryImpl) bind DataRepository::class
    singleOf(::NoteRepositoryImpl) bind NoteRepository::class
    singleOf(::LinkRepositoryImpl) bind LinkRepository::class
    singleOf(::NoteAndLinkRepositoryImpl) bind NoteAndLinkRepository::class
    singleOf(::TagRepositoryImpl) bind TagRepository::class
    singleOf(::NoteAndTagRepositoryImpl) bind NoteAndTagRepository::class
    singleOf(::TaskRepositoryImpl) bind TaskRepository::class
    singleOf(::NoteAndTaskRepositoryImpl) bind NoteAndTaskRepository::class
    singleOf(::AudioRepositoryImpl) bind AudioRepository::class
    singleOf(::NoteAndAudioRepositoryImpl) bind NoteAndAudioRepository::class
    singleOf(::WidgetRepositoryImpl) bind WidgetRepository::class
    singleOf(::RootRepositoryImpl) bind RootRepository::class

    //UseCases.
    factoryOf(DataUseCase::AddData)
    factoryOf(DataUseCase::EditData)
    factoryOf(DataUseCase::DeleteData)
    factoryOf(DataUseCase::DeleteAllTrashedData)

    factoryOf(LinkUseCase::AddLink)
    factoryOf(LinkUseCase::DeleteLink)
    factoryOf(LinkUseCase::GetAllLinks)

    factoryOf(NoteAndLinkUseCase::AddNoteAndLink)
    factoryOf(NoteAndLinkUseCase::DeleteNoteAndLink)
    factoryOf(NoteAndLinkUseCase::GetAllNotesAndLinks)

    factoryOf(NoteAndTagUseCase::AddNoteAndTag)
    factoryOf(NoteAndTagUseCase::DeleteNoteAndTag)
    factoryOf(NoteAndTagUseCase::GetAllNotesAndTags)

    factoryOf(NoteAndTaskUseCase::AddNoteAndTask)
    factoryOf(NoteAndTaskUseCase::DeleteNoteAndTask)
    factoryOf(NoteAndTaskUseCase::GetAllNotesAndTask)

    factoryOf(NoteUseCase::GetAllNotesById)
    factoryOf(NoteUseCase::GetAllNotesByName)
    factoryOf(NoteUseCase::GetAllNotesByNewest)
    factoryOf(NoteUseCase::GetAllRemindingNotes)
    factoryOf(NoteUseCase::GetAllNotesByOldest)
    factoryOf(NoteUseCase::GetAllNotesByPriority)
    factoryOf(NoteUseCase::GetAllTrashedNotes)

    factoryOf(TagUseCase::AddTag)
    factoryOf(TagUseCase::DeleteTag)
    factoryOf(TagUseCase::UpdateTag)
    factoryOf(TagUseCase::GetAllTags)

    factoryOf(TaskUseCase::AddTaskItem)
    factoryOf(TaskUseCase::DeleteTaskItem)
    factoryOf(TaskUseCase::UpdateTaskItem)
    factoryOf(TaskUseCase::GetAllTaskItems)

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
}