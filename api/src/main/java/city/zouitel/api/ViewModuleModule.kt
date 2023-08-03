package city.zouitel.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ViewModuleModule {

    @Provides
    fun provideViewModel(apiRepositoryImpl: ApiRepositoryImpl) = ApiViewModel(apiRepositoryImpl)

}