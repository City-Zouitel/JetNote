package city.zouitel.audios.di

import com.google.android.exoplayer2.ExoPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import city.zouitel.audios.ui.MediaPlayerViewModel
import org.koin.core.module.dsl.singleOf
import city.zouitel.domain.exoplayer.*
import org.koin.dsl.bind

val exoPlayerKoinModule = module {
    single {
        ExoPlayer.Builder(androidContext()).build().apply {
            setHandleAudioBecomingNoisy(true)
        }
    }

    singleOf(::ExoPlayerImpl) bind ExoRepo::class

    viewModelOf(::MediaPlayerViewModel)
}