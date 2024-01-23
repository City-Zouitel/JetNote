package city.zouitel.domain.di

import city.zouitel.domain.usecase.DataUseCase
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.domain.usecase.NoteAndLinkUseCase
import city.zouitel.domain.usecase.NoteAndTagUseCase
import city.zouitel.domain.usecase.NoteAndTaskUseCase
import city.zouitel.domain.usecase.NoteUseCase
import city.zouitel.domain.usecase.TagUseCase
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.domain.usecase.WidgetUseCase
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

@OptIn(KoinInternalApi::class)
val useCasesKoinModule = module {
//    includedModules.add(repositoryKoinModule)

//    factoryOf(DataUseCase::AddData)
//    factoryOf(DataUseCase::EditData)
//    factoryOf(DataUseCase::DeleteData)
//    factoryOf(DataUseCase::DeleteAllTrashedData)
//
//    factoryOf(LinkUseCase::AddLink)
//    factoryOf(LinkUseCase::DeleteLink)
//    factoryOf(LinkUseCase::GetAllLinks)
//
//    factoryOf(NoteAndLinkUseCase::AddNoteAndLink)
//    factoryOf(NoteAndLinkUseCase::DeleteNoteAndLink)
//    factoryOf(NoteAndLinkUseCase::GetAllNotesAndLinks)
//
//    factoryOf(NoteAndTagUseCase::AddNoteAndTag)
//    factoryOf(NoteAndTagUseCase::DeleteNoteAndTag)
//    factoryOf(NoteAndTagUseCase::GetAllNotesAndTags)
//
//    factoryOf(NoteAndTaskUseCase::AddNoteAndTask)
//    factoryOf(NoteAndTaskUseCase::DeleteNoteAndTask)
//    factoryOf(NoteAndTaskUseCase::GetAllNotesAndTask)
//
//    factoryOf(NoteUseCase::GetAllNotesById)
//    factoryOf(NoteUseCase::GetAllNotesByName)
//    factoryOf(NoteUseCase::GetAllNotesByNewest)
//    factoryOf(NoteUseCase::GetAllRemindingNotes)
//    factoryOf(NoteUseCase::GetAllNotesByOldest)
//    factoryOf(NoteUseCase::GetAllNotesByPriority)
//    factoryOf(NoteUseCase::GetAllTrashedNotes)
//
//    factoryOf(TagUseCase::AddTag)
//    factoryOf(TagUseCase::DeleteTag)
//    factoryOf(TagUseCase::UpdateTag)
//    factoryOf(TagUseCase::GetAllTags)
//
//    factoryOf(TaskUseCase::AddTaskItem)
//    factoryOf(TaskUseCase::DeleteTaskItem)
//    factoryOf(TaskUseCase::UpdateTaskItem)
//    factoryOf(TaskUseCase::GetAllTaskItems)
//
//    factoryOf(WidgetUseCase::GetAllWidgetMainEntityById)
}