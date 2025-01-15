package city.zouitel.note.di

import cafe.adriel.voyager.core.registry.screenModule
import city.zouitel.domain.provider.SharedScreen
import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.providers.Options
import city.zouitel.note.providers.Rational
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.ui.bottom_bar.OptionsScreen
import city.zouitel.note.ui.bottom_bar.RationalScreen
import city.zouitel.note.ui.workplace.WorkplaceScreen
import city.zouitel.note.ui.workplace.WorkplaceScreenModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val workplaceDIModule = module {

    factoryOf(::DataMapper)
    factoryOf(::WorkplaceScreenModel)
    factory {
        DataScreenModel(get(), get(), get(), get(), get())
    }
}

@OptIn(ExperimentalPermissionsApi::class)
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
            priorityState = it.priorityState,
            imageLaunch = it.imageLaunch
        )
    }

    register<Rational> {
        RationalScreen(
            permissionState = it.permissionState,
            permissionName = it.permissionName
        )
    }
}