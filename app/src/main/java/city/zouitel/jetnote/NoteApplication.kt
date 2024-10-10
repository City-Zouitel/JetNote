package city.zouitel.jetnote

import android.app.Application
import android.os.Build
import city.zouitel.audios.di.audioPlayerKoinModule
import city.zouitel.database.di.databaseKoinModule
import city.zouitel.datastore.datastoreKoinModule
import city.zouitel.init.initializerKoinModule
import city.zouitel.links.di.linksKoinModule
import city.zouitel.media.di.mediaKoinModule
import city.zouitel.note.di.noteKoinModule
import city.zouitel.notifications.di.notificationKoinModule
import city.zouitel.quicknote.di.quickNoteKoinModule
import city.zouitel.recoder.di.recorderKoinModule
import city.zouitel.repository.di.repositoryKoinModule
import city.zouitel.root.di.rootKoinModule
import city.zouitel.screens.di.screensKoinModule
import city.zouitel.security.di.rootCheckerKoinModule
import city.zouitel.systemDesign.di.commonSystemDesignKoinModule
import city.zouitel.tags.di.tagsKoinModule
import city.zouitel.tasks.di.tasksKoinModule
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
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NoteApplication: Application(), KoinComponent, ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@NoteApplication)
            workManagerFactory()
            modules(
                databaseKoinModule,
                datastoreKoinModule,
                repositoryKoinModule,
                rootCheckerKoinModule,
                audioPlayerKoinModule,
                linksKoinModule,
                noteKoinModule,
                quickNoteKoinModule,
                recorderKoinModule,
                tagsKoinModule,
                tasksKoinModule,
                widgetKoinModule,
                notificationKoinModule,
                commonSystemDesignKoinModule,
                screensKoinModule,
                initializerKoinModule,
                rootKoinModule,
                mediaKoinModule
            )
        }

        /**
         * Global Exception Handler.
         */
//        GlobalExceptionHandler.initialize(this, NoteActivity::class.java)

        /**
         *
         */
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