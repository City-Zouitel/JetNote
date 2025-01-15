package city.zouitel.audios.di

import cafe.adriel.voyager.core.registry.screenModule
import city.zouitel.audios.audio.AudioManager
import city.zouitel.audios.audio.AudioRepository
import city.zouitel.audios.audio.LocalMediaDataSource
import city.zouitel.audios.mapper.AudioMapper
import city.zouitel.audios.mapper.NoteAndAudioMapper
import city.zouitel.audios.ui.component.AudioScreenModel
import city.zouitel.audios.ui.component.NoteAndAudioScreenModel
import city.zouitel.audios.ui.list.AudioListScreen
import city.zouitel.audios.ui.list.AudioListScreenModel
import city.zouitel.domain.exoplayer.ExoPlayerImpl
import city.zouitel.domain.exoplayer.ExoRepo
import city.zouitel.domain.provider.SharedScreen
import city.zouitel.systemDesign.CommonConstants.REC_DIR
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.Dispatchers
import linc.com.amplituda.Amplituda
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val audioPlayerDIModule = module {
    single { Dispatchers.IO }

    single {
        ExoPlayer.Builder(androidContext()).build().apply {
            setHandleAudioBecomingNoisy(true)
        }
    }

//    single {
//        AudioAttributes.Builder()
//            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//            .setUsage(AudioAttributes.USAGE_MEDIA)
//            .setLegacyStreamType(android.media.AudioManager.STREAM_MUSIC)
//            .build()
//    }

    singleOf(::ExoPlayerImpl) bind ExoRepo::class

    single { Amplituda(androidContext()) }
    single { AudioManager(get(), get()) }
    single { AudioRepository(get(), get(), get()) }
    single { LocalMediaDataSource(androidContext(), get()) }

    factory { AudioScreenModel(get(), get(),get(),get(), get(named(REC_DIR))) }
    factory { AudioListScreenModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { NoteAndAudioScreenModel(get(), get(), get()) }

    factoryOf(::AudioMapper)
    factoryOf(::NoteAndAudioMapper)
}

val audioPlayerScreenModule = screenModule {
    register<SharedScreen.AudioList> {
        AudioListScreen(it.uid)
    }
}