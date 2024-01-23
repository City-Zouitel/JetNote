package city.zouitel.database.di

import city.zouitel.database.datasourceImpl.*
import city.zouitel.repository.datasource.*

//@Module
//@InstallIn(SingletonComponent::class)
interface DataSourceModule {

//    @Binds
//    @Singleton
    fun bindLinkDataSource(data: LinkDataSourceImpl): LinkDataSource

//    @Binds
//    @Singleton
    fun bindDataDataSource(data: DataDataSourceImpl): DataDataSource

//    @Binds
//    @Singleton
    fun bindNoteAndLinkDataSource(data: NoteAndLinkDataSourceImpl): NoteAndLinkDataSource

//    @Binds
//    @Singleton
    fun bindNoteAndTagDataSource(data: NoteAndTagDataSourceImpl): NoteAndTagDataSource

//    @Binds
//    @Singleton
    fun bindNoteAndTaskDataSource(data: NoteAndTaskDataSourceImpl): NoteAndTaskDataSource

//    @Binds
//    @Singleton
    fun bindNoteDataSource(data: NoteDataSourceImpl): NoteDataSource

//    @Binds
//    @Singleton
    fun bindTagDataSource(data: TagDataSourceImpl): TagDataSource

//    @Binds
//    @Singleton
    fun bindTaskDataSource(data: TaskDataSourceImpl): TaskDataSource

//    @Binds
//    @Singleton
    fun bindWidgetDataSource(data: WidgetDataSourceImpl): WidgetDataSource
}