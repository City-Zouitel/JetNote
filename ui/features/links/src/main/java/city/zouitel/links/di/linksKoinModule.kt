package city.zouitel.links.di

import androidx.work.WorkerFactory
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import city.zouitel.links.ui.*
import city.zouitel.links.mapper.*
import city.zouitel.links.worker.LinkWorker
import city.zouitel.systemDesign.Cons.LINK_DIR
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named

val linksKoinModule = module {
    factoryOf(::LinkMapper)
    factoryOf(::NoteAndLinkMapper)

    viewModel {
        LinkVM(get(), get(), get(), get(), get(named(LINK_DIR)))
    }

    viewModelOf(::NoteAndLinkVM)

    worker {
        LinkWorker(androidContext(), get(), get(), get(), get(), get(), get())
    }
}