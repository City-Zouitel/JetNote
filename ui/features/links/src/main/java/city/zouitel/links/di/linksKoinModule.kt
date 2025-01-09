package city.zouitel.links.di

import city.zouitel.links.mapper.LinkMapper
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.links.worker.LinkWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val linksKoinModule = module {
    factoryOf(::LinkMapper)

    factory {
        LinkScreenModel(get(), get(), get(), get(), get())
    }

    worker {
        LinkWorker(androidContext(), get(), get(), get(), get())
    }
}