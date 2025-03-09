package city.zouitel.note.di

import cafe.adriel.voyager.core.registry.screenModule
import city.zouitel.domain.provider.SharedScreen
import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.providers.Options
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.ui.workplace.OptionsScreen
import city.zouitel.note.ui.workplace.WorkplaceScreen
import city.zouitel.note.ui.workplace.WorkplaceScreenModel
import city.zouitel.note.worker.CopyMediaWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val workplaceDIModule = module {

    factoryOf(::DataMapper)
    factory {
        WorkplaceScreenModel(get())
    }
    factory {
        DataScreenModel(get(), get(), get(), get(), get(), get())
    }
    worker {
        CopyMediaWorker(androidContext(), get(), get(), get(), get())
    }
}

val workplaceScreenModule = screenModule {
    register<SharedScreen.Workplace> {
        WorkplaceScreen(
            uid = it.uid,
            isNew = it.isNew,
            title = it.title,
            description = it.description,
            backgroundColor = it.backgroundColor,
            textColor = it.textColor,
            priority = it.priority
        )
    }

    register<Options> {
        OptionsScreen(
            uid = it.uid,
            titleState = it.titleState,
            descriptionState = it.descriptionState,
            priorityState = it.priorityState
        )
    }
}