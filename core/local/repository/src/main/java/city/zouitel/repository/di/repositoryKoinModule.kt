package city.zouitel.repository.di

import city.zouitel.domain.repository.*
import city.zouitel.domain.usecase.*
import city.zouitel.repository.mapper.*
import city.zouitel.repository.repositoryImpl.*
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
    factoryOf(::MediaMapper)
    factoryOf(::NoteAndMediaMapper)
    factory {
        NoteMapper(get(), get(), get(), get(), get(), get())
    }
    factory {
        WidgetMapper(get(), get(), get(), get(), get(), get())
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
    singleOf(::AudioRepoImpl) bind AudioRepo::class
    singleOf(::NoteAndAudioRepositoryImpl) bind NoteAndAudioRepository::class
    singleOf(::WidgetRepositoryImpl) bind WidgetRepository::class
    singleOf(::RootRepositoryImpl) bind RootRepository::class
    singleOf(::MediaRepoImpl) bind MediaRepository::class
    singleOf(::NoteAndMediaRepoImpl) bind NoteAndMediaRepository::class

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
    factoryOf(NoteUseCase::GetAllRemovedNotes)

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

    factoryOf(MediaUseCase::GetAllMedias)
    factoryOf(MediaUseCase::AddMedia)
    factoryOf(MediaUseCase::UpdateMedia)
    factoryOf(MediaUseCase::DeleteMedia)

    factoryOf(NoteAndMediaUseCase::GetAllNotesAndMedia)
    factoryOf(NoteAndMediaUseCase::AddNoteAndMedia)
    factoryOf(NoteAndMediaUseCase::UpdateNoteAndMedia)
    factoryOf(NoteAndMediaUseCase::DeleteNoteAndMedia)
}