package city.zouitel.quicknote.di

import city.zouitel.quicknote.mapper.QuickDataMapper

//@Module
//@InstallIn(SingletonComponent::class)
object QuickDataModule {

//    @Singleton
//    @Provides
    fun provideQuickDataMapper() = QuickDataMapper()
}