package city.zouitel.audios.di

import cafe.adriel.voyager.core.registry.screenModule
import city.zouitel.audios.audio.AudioManager
import city.zouitel.audios.audio.AudioRepository
import city.zouitel.audios.audio.LocalMediaDataSource
import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.audios.player.PlaybackManager
import city.zouitel.audios.ui.component.AudioScreenModel
import city.zouitel.audios.ui.list.AudioListScreen
import city.zouitel.audios.ui.list.AudioListScreenModel
import city.zouitel.domain.exoplayer.ExoPlayerImpl
import city.zouitel.domain.exoplayer.ExoRepo
import city.zouitel.domain.provider.SharedScreen
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.Dispatchers
import linc.com.amplituda.Amplituda
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val audioPlayerDIModule = module {
    single { Dispatchers.IO }

    single {
        ExoPlayer.Builder(androidContext()).build().apply {
            setHandleAudioBecomingNoisy(true)
        }
    }

    singleOf(::ExoPlayerImpl) bind ExoRepo::class

    single { Amplituda(androidContext()) }
    single { AudioManager(get(), get()) }
    single { AudioRepository(get(), get(), get()) }
    single { LocalMediaDataSource(androidContext(), get()) }

    factory { AudioScreenModel(get(), get(),get(),get(), get()) }
    factory { AudioListScreenModel(get(), get(), get(), get(), get()) }

    factoryOf(::AudioMapper)

    single { PlaybackManager(androidContext(), get()) }
}

val audioPlayerScreenModule = screenModule {
    register<SharedScreen.AudioList> {
        AudioListScreen(it.uid)
    }
}