package city.zouitel.links.di

import org.koin.dsl.module
import city.zouitel.links.ui.*
import city.zouitel.links.mapper.*
import city.zouitel.links.worker.LinkWorker
import city.zouitel.systemDesign.CommonConstants.LINK_DIR
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named

val linksKoinModule = module {
    factoryOf(::LinkMapper)
    factoryOf(::NoteAndLinkMapper)

    factory {
        LinkScreenModel(get(), get(), get(), get())
    }
    factory {
        NoteAndLinkScreenModel(get(), get(), get())
    }

    worker {
        LinkWorker(androidContext(), get(), get(), get(), get(), get(), get())
    }
}