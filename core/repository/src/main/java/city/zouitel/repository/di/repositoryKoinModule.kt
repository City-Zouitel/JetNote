package city.zouitel.repository.di

import city.zouitel.domain.repository.DataRepository
import city.zouitel.domain.repository.LinkRepository
import city.zouitel.domain.repository.NoteAndLinkRepository
import city.zouitel.domain.repository.NoteAndTagRepository
import city.zouitel.domain.repository.NoteAndTaskRepository
import city.zouitel.domain.repository.NoteRepository
import city.zouitel.domain.repository.RootRepository
import city.zouitel.domain.repository.TagRepository
import city.zouitel.domain.repository.TaskRepository
import city.zouitel.domain.repository.WidgetRepository
import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.domain.usecase.LinkUseCase
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
import city.zouitel.repository.mapper.NoteMapper
import city.zouitel.repository.mapper.RootMapper
import city.zouitel.repository.mapper.TagMapper
import city.zouitel.repository.mapper.TaskMapper
import city.zouitel.repository.mapper.WidgetMapper
import city.zouitel.repository.repositoryImpl.DataRepositoryImpl
import city.zouitel.repository.repositoryImpl.LinkRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteAndLinkRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteAndTagRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteAndTaskRepositoryImpl
import city.zouitel.repository.repositoryImpl.NoteRepositoryImpl
import city.zouitel.repository.repositoryImpl.RootRepositoryImpl
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
    factoryOf(::NoteAndLinkMapper)
    factoryOf(::NoteAndTagMapper)
    factoryOf(::NoteAndTaskMapper)
    factoryOf(::RootMapper)
    factory {
        NoteMapper(get(), get(), get(), get())
    }

    factory {
        WidgetMapper(get(), get(), get(), get())
    }

    //Repositories.
    singleOf(::LinkRepositoryImpl) bind LinkRepository::class
    singleOf(::DataRepositoryImpl) bind DataRepository::class
    singleOf(::NoteAndLinkRepositoryImpl) bind NoteAndLinkRepository::class
    singleOf(::NoteAndTagRepositoryImpl) bind NoteAndTagRepository::class
    singleOf(::NoteAndTaskRepositoryImpl) bind NoteAndTaskRepository::class
    singleOf(::NoteRepositoryImpl) bind NoteRepository::class
    singleOf(::TagRepositoryImpl) bind TagRepository::class
    singleOf(::TaskRepositoryImpl) bind TaskRepository::class
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
}