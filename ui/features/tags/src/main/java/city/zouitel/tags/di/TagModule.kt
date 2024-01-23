package city.zouitel.tags.di

import city.zouitel.tags.mapper.NoteAndTagMapper
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tags.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

//@Module
//@InstallIn(SingletonComponent::class)
object TagModule {

//    @Singleton
//    @Provides
    fun provideTagMapper() = TagMapper()

//    @Singleton
//    @Provides
    fun provideNoteAndTagMapper() = NoteAndTagMapper()
}

val tagsKoinModule = module {
    factoryOf(::TagMapper)
    factoryOf(::NoteAndTagMapper)

    viewModelOf(::NoteAndTagViewModel)
    viewModelOf(::TagViewModel)
}