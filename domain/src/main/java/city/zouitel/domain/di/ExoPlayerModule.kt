package city.zouitel.domain.di

import city.zouitel.domain.exoplayer.ExoPlayerImpl
import com.google.android.exoplayer2.ExoPlayer

//@Module
//@InstallIn(SingletonComponent::class)
object ExoPlayerModule {

//    @Singleton
//    @Provides
    fun provideExoPlayer(exo: ExoPlayer) = ExoPlayerImpl(exo)
}