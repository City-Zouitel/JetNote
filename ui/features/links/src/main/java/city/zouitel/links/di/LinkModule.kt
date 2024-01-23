package city.zouitel.links.di

import android.content.Context
import city.zouitel.links.mapper.LinkMapper
import city.zouitel.links.mapper.NoteAndLinkMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

//@Module
//@InstallIn(SingletonComponent::class)
object LinkModule {

//    @Singleton
//    @Provides
    fun provideContext(/*@ApplicationContext*/ context: Context) = context

//    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

//    @Singleton
//    @Provides
    fun provideLinkMapper() = LinkMapper()

//    @Singleton
//    @Provides
    fun provideNoteAndLinkMapper() = NoteAndLinkMapper()
}