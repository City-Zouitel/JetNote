package city.zouitel.audios.di

import android.media.AudioAttributes
import city.zouitel.audios.media.LocalMediaDataSource
import city.zouitel.audios.media.AudioListViewModel
import city.zouitel.audios.media.AudioManager
import city.zouitel.audios.media.AudioRepository
import com.google.android.exoplayer2.ExoPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import city.zouitel.audios.ui.AudioScreenModel
import org.koin.core.module.dsl.singleOf
import city.zouitel.domain.exoplayer.*
import city.zouitel.systemDesign.Cons.REC_DIR
import kotlinx.coroutines.Dispatchers
import linc.com.amplituda.Amplituda
import org.koin.androidx.viewmodel.dsl.viewModel
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

    factory { AudioScreenModel(get(), get(), get(named(REC_DIR))) }
    viewModel { AudioListViewModel(androidContext(), get()) }

}