package city.zouitel.note.di

import city.zouitel.links.mapper.LinkMapper
import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.*
import city.zouitel.note.mapper.NoteMapper
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tasks.mapper.TaskMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

//@Module
//@InstallIn(SingletonComponent::class)
object NoteModule {

//    @Singleton
//    @Provides
    fun provideDataMapper() = DataMapper()

//    @Singleton
//    @Provides
    fun provideNoteMapper(
        dataMapper: DataMapper,
        tagMapper: TagMapper,
        taskMapper: TaskMapper,
        linkMapper: LinkMapper
    ) = NoteMapper(dataMapper, tagMapper, taskMapper, linkMapper)
}

val noteKoinModule = module {
    factoryOf(::DataMapper)
    factory {
        NoteMapper(get(), get(), get(), get())
    }

    viewModelOf(::DataViewModel)
    viewModelOf(::NoteViewModel)
}