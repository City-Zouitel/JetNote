package city.zouitel.links.di

import androidx.work.WorkerFactory
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import city.zouitel.links.ui.*
import city.zouitel.links.mapper.*
import city.zouitel.links.worker.LinkWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.factoryOf

val linksKoinModule = module {
    factoryOf(::LinkMapper)
    factoryOf(::NoteAndLinkMapper)

    viewModel {
        LinkVM(get(), get(), get(), get())
    }
    viewModelOf(::NoteAndLinkVM)

    worker {
        LinkWorker(androidContext(), get(), get(), get(), get(), get(), get())
    }
}