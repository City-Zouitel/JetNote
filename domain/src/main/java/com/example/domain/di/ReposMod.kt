package com.example.domain.di

import com.example.domain.repos.*
import com.example.domain.reposImpl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ReposMod {

    @Binds
     fun entityBinds(impl: EntityRepoImpl): EntityRepo

    @Binds
     fun exoBinds(impl: ExoRepoImpl): ExoRepo

    @Binds
     fun labelBinds(impl: LabelRepoImpl): LabelRepo

    @Binds
     fun noteAndLabelBinds(impl: NoteAndLabelRepoImpl): NoteAndLabelRepo

    @Binds
     fun linkBinds(impl: LinkRepoImpl): LinkRepo

//    @Binds
//    abstract fun noteAndLinkBinds(impl: NoteAndLinkRepoImpl): NoteAndLabelRepo

    @Binds
     fun todoBinds(impl: TodoRepoImpl): TodoRepo

    @Binds
     fun noteAndTodoBinds(impl: NoteAndTodoRepoImpl): NoteAndTodoRepo

    @Binds
     fun noteBinds(impl: NoteRepoImpl): NoteRepo

    @Binds
     fun widgetBinds(impl: WidgetEntityRepoImpl): WidgetEntityRepo
}