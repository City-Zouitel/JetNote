package city.zouitel.audios.di

import city.zouitel.audios.AudioManager
import city.zouitel.audios.AudioRepository
import com.google.android.exoplayer2.ExoPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import city.zouitel.audios.ui.MediaPlayerViewModel
import org.koin.core.module.dsl.singleOf
import city.zouitel.domain.exoplayer.*
import kotlinx.coroutines.Dispatchers
import linc.com.amplituda.Amplituda
import org.koin.dsl.bind

val exoPlayerKoinModule = module {
    single { Dispatchers.IO }

    single {
        ExoPlayer.Builder(androidContext()).build().apply {
            setHandleAudioBecomingNoisy(true)
        }
    }

    singleOf(::ExoPlayerImpl) bind ExoRepo::class
    singleOf(::Amplituda)
    single { AudioManager(get(), get()) }
    single { AudioRepository(get(), get()) }

    viewModelOf(::MediaPlayerViewModel)
}