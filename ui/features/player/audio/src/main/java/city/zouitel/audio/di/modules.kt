package city.zouitel.audio.di

import androidx.media3.common.Player
import cafe.adriel.voyager.core.registry.screenModule
import city.zouitel.audio.audio.AudioManager
import city.zouitel.audio.audio.AudioRepository
import city.zouitel.audio.audio.LocalMediaDataSource
import city.zouitel.audio.mapper.AudioMapper
import city.zouitel.audio.mapper.RecordMapper
import city.zouitel.audio.player.PlaybackManager
import city.zouitel.audio.ui.component.AudioScreenModel
import city.zouitel.audio.ui.list.AudioListScreen
import city.zouitel.audio.ui.list.AudioListScreenModel
import city.zouitel.audio.ui.record.RecordScreenModel
import city.zouitel.domain.provider.SharedScreen
import kotlinx.coroutines.Dispatchers
import linc.com.amplituda.Amplituda
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val audioPlayerDIModule = module {
    single { Dispatchers.IO }

    single { Amplituda(androidContext()) }
    single { AudioManager(get(), get()) }
    single { AudioRepository(get(), get(), get()) }
    single { LocalMediaDataSource(androidContext(), get()) }

    factory { AudioScreenModel(get(), get(), get(), get(), get(), get()) }
    factory { AudioListScreenModel(get(), get(), get()) }
    factory { RecordScreenModel(get(), get(), get(), get(), get()) }

    factoryOf(::AudioMapper)
    factoryOf(::RecordMapper)

    singleOf(::PlaybackManager) bind Player.Listener::class

    single { PlaybackManager(androidContext(), get()) }
}

val audioPlayerScreenModule = screenModule {
    register<SharedScreen.AudioList> {
        AudioListScreen(it.uid)
    }
}