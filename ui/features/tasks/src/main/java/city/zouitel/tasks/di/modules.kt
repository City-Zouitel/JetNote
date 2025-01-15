package city.zouitel.tasks.di

import cafe.adriel.voyager.core.registry.screenModule
import city.zouitel.domain.provider.SharedScreen
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.tasks.ui.TaskScreenModel
import city.zouitel.tasks.ui.TasksScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val tasksKoinModule = module {
    factoryOf(::TaskMapper)
    factoryOf(::TaskScreenModel)
}

val tasksScreenModule = screenModule {
    register<SharedScreen.Tasks> {
        TasksScreen(it.uid)
    }
}