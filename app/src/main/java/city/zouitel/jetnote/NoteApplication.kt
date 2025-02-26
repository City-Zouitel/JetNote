package city.zouitel.jetnote

import android.app.Application
import android.os.Build
import cafe.adriel.voyager.core.registry.ScreenRegistry
import city.zouitel.assistant.di.assistantScreenModule
import city.zouitel.audio.di.audioPlayerDIModule
import city.zouitel.audio.di.audioPlayerScreenModule
import city.zouitel.base.di.baseKoinModule
import city.zouitel.database.di.databaseDIModule
import city.zouitel.datastore.datastoreKoinModule
import city.zouitel.generativeai.di.generativeKoinModule
import city.zouitel.init.initializerKoinModule
import city.zouitel.links.di.linksKoinModule
import city.zouitel.media.di.mediaKoinModule
import city.zouitel.networkRepo.di.networkRepoDIModule
import city.zouitel.note.di.workplaceDIModule
import city.zouitel.note.di.workplaceScreenModule
import city.zouitel.notifications.di.notificationKoinModule
import city.zouitel.permissions.permissionsDIModule
import city.zouitel.playback.di.playbackDIModule
import city.zouitel.quicknote.di.quickNoteKoinModule
import city.zouitel.recoder.di.recorderKoinModule
import city.zouitel.reminder.di.reminderDIModule
import city.zouitel.reminder.di.reminderScreenModule
import city.zouitel.repository.di.localRepoDIModule
import city.zouitel.rooted.di.rootedKoinModule
import city.zouitel.screens.di.screensKoinModule
import city.zouitel.screens.di.screensScreenModule
import city.zouitel.systemDesign.di.commonSystemDesignKoinModule
import city.zouitel.tags.di.tagsKoinModule
import city.zouitel.tags.di.tagsScreenModule
import city.zouitel.tasks.di.tasksKoinModule
import city.zouitel.tasks.di.tasksScreenModule
import city.zouitel.widget.di.widgetKoinModule
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.karacca.beetle.Beetle
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NoteApplication: Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@NoteApplication)
            workManagerFactory()
            modules(
                databaseDIModule,
                baseKoinModule,
                datastoreKoinModule,
                localRepoDIModule,
                networkRepoDIModule,
                generativeKoinModule,
                audioPlayerDIModule,
                linksKoinModule,
                workplaceDIModule,
                quickNoteKoinModule,
                recorderKoinModule,
                tagsKoinModule,
                tasksKoinModule,
                widgetKoinModule,
                notificationKoinModule,
                commonSystemDesignKoinModule,
                screensKoinModule,
                initializerKoinModule,
                rootedKoinModule,
                mediaKoinModule,
                reminderDIModule,
                playbackDIModule,
                permissionsDIModule
            )
        }

        ScreenRegistry {
            screensScreenModule()
            workplaceScreenModule()
            assistantScreenModule()
            audioPlayerScreenModule()
            reminderScreenModule()
            tagsScreenModule()
            tasksScreenModule()
        }

        Beetle.configure {
            enableAssignees = true
            enableLabels = true
        }
        Beetle.init(this, "City-Zouitel", "JetNote")
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this)
            .newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(.5)
                    .weakReferencesEnabled(true)
                    .build()
            }
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }
}