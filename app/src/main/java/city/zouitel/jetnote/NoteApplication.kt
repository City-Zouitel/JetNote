package city.zouitel.jetnote

import android.app.Application
import city.zouitel.audios.di.exoPlayerKoinModule
import city.zouitel.database.di.databaseKoinModule
import city.zouitel.datastore.datastoreKoinModule
import city.zouitel.init.initializerKoinModule
import city.zouitel.links.di.linksKoinModule
import city.zouitel.screens.di.navigationKoinModule
import city.zouitel.note.di.noteKoinModule
import city.zouitel.notifications.di.notificationKoinModule
import city.zouitel.quicknote.di.quickNoteKoinModule
import city.zouitel.recoder.di.recorderKoinModule
import city.zouitel.repository.di.repositoryKoinModule
import city.zouitel.root.di.rootKoinModule
import city.zouitel.security.di.securityKoinModule
import city.zouitel.systemDesign.di.datastoreVMKoinModule
import city.zouitel.tags.di.tagsKoinModule
import city.zouitel.tasks.di.tasksKoinModule
import city.zouitel.widget.di.widgetKoinModule
import com.karacca.beetle.Beetle
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NoteApplication: Application(), KoinComponent {

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
                securityKoinModule,
                exoPlayerKoinModule,
                linksKoinModule,
                noteKoinModule,
                quickNoteKoinModule,
                recorderKoinModule,
                tagsKoinModule,
                tasksKoinModule,
                widgetKoinModule,
                notificationKoinModule,
                datastoreVMKoinModule,
                navigationKoinModule,
                initializerKoinModule,
                rootKoinModule
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
}