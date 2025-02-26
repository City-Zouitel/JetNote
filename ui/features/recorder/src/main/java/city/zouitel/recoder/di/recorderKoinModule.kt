package city.zouitel.recoder.di

import androidx.media3.exoplayer.ExoPlayer
import city.zouitel.recoder.mapper.RecordMapper
import city.zouitel.recoder.ui.RecorderRepo
import city.zouitel.recoder.ui.RecorderRepoImpl
import city.zouitel.recoder.ui.RecorderScreenModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val recorderKoinModule = module {

    single {
        RecorderRepoImpl(androidContext())
    }

    singleOf(::RecorderRepoImpl) bind RecorderRepo::class

    factoryOf(::RecorderScreenModel)
    factoryOf(::RecordMapper)

    single {
        ExoPlayer.Builder(androidContext())
            .setHandleAudioBecomingNoisy(true)
            .build()
    }
}