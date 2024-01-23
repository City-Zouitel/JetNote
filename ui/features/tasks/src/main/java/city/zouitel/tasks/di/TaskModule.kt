package city.zouitel.tasks.di

import city.zouitel.tasks.mapper.NoteAndTaskMapper
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.tasks.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

//@Module
//@InstallIn(SingletonComponent::class)
object TaskModule {

//    @Singleton
//    @Provides
    fun provideTaskMapper() = TaskMapper()

//    @Singleton
//    @Provides
    fun provideNoteAndTaskMapper() = NoteAndTaskMapper()
}

val tasksKoinModule = module {
    factoryOf(::TaskMapper)
    factoryOf(::NoteAndTaskMapper)

    viewModelOf(::TaskViewModel)
    viewModelOf(::NoteAndTaskViewModel)
}