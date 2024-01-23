package city.zouitel.links.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import city.zouitel.links.ui.*
import city.zouitel.links.mapper.*
import city.zouitel.links.worker.LinkWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.factoryOf

val linksKoinModule = module {
    factoryOf(::LinkMapper)
    factoryOf(::NoteAndLinkMapper)

    viewModelOf(::LinkVM)
    viewModelOf(::NoteAndLinkVM)
    workerOf(::LinkWorker)
}