package city.zouitel.playback.di

import androidx.media3.session.MediaSessionService
import city.zouitel.playback.PlaybackService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val playbackDIModule = module {
    singleOf(::PlaybackService) bind MediaSessionService::class
}