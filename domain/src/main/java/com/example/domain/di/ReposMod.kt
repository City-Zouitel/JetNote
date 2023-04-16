package com.example.domain.di

import com.example.domain.repos.*
import com.example.domain.reposImpl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReposMod {

    @Binds
    abstract fun entityBinds(impl: EntityRepoImpl): EntityRepo

    @Binds
    abstract fun exoBinds(impl: ExoRepoImpl): ExoRepo

    @Binds
    abstract fun labelBinds(impl: LabelRepoImpl): LabelRepo

    @Binds
    abstract fun noteAndLabelBinds(impl: NoteAndLabelRepoImpl): NoteAndLabelRepo

    @Binds
    abstract fun linkBinds(impl: LinkRepoImpl): LinkRepo

//    @Binds
//    abstract fun noteAndLinkBinds(impl: NoteAndLinkRepoImpl): NoteAndLabelRepo

    @Binds
    abstract fun todoBinds(impl: TodoRepoImpl): TodoRepo

    @Binds
    abstract fun noteAndTodoBinds(impl: NoteAndTodoRepoImpl): NoteAndTodoRepo

    @Binds
    abstract fun noteBinds(impl: NoteRepoImpl): NoteRepo

    @Binds
    abstract fun widgetBinds(impl: WidgetEntityRepoImpl): WidgetEntityRepo
}