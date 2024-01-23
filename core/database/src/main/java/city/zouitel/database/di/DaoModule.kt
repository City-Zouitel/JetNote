package city.zouitel.database.di

import city.zouitel.database.Database

//@Module
//@InstallIn(SingletonComponent::class)
object DaoModule {

//    @Singleton
//    @Provides
    fun provideNoteDao(database: Database) = database.getNoteDao()

//    @Singleton
//    @Provides
    fun provideLabelDao(database: Database) = database.getLabelDao()

//    @Singleton
//    @Provides
    fun provideNoteAndLabelDao(database: Database) = database.getNoteAndLabelDao()

//    @Singleton
//    @Provides
    fun provideEntityDao(database: Database) = database.getEntityDao()

//    @Singleton
//    @Provides
    fun provideWidgetEntityDao(database: Database) = database.getWidgetEntityDao()

//    @Singleton
//    @Provides
    fun provideTodoDao(database: Database) = database.getTodoDao()

//    @Singleton
//    @Provides
    fun provideNoteAndTodoDao(database: Database) = database.getNoteAndTodoDao()

//    @Singleton
//    @Provides
    fun provideLinkDao(database: Database) = database.getLinkDao()

//    @Singleton
//    @Provides
    fun provideNoteAndLinkDao(database: Database) = database.getNoteAndLink()
}