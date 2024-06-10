package city.zouitel.audios.di

import city.zouitel.audios.audio.*
import city.zouitel.audios.mapper.*
import city.zouitel.audios.ui.list.AudioListScreenModel
import com.google.android.exoplayer2.ExoPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import city.zouitel.audios.ui.component.AudioScreenModel
import city.zouitel.audios.ui.component.NoteAndAudioScreenModel
import org.koin.core.module.dsl.singleOf
import city.zouitel.domain.exoplayer.*
import city.zouitel.systemDesign.CommonConstants.REC_DIR
import kotlinx.coroutines.Dispatchers
import linc.com.amplituda.Amplituda
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind

val audioPlayerKoinModule = module {
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