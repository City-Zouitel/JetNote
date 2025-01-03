package city.zouitel.tasks.di

import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.tasks.ui.TaskScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val tasksKoinModule = module {
    factoryOf(::TaskMapper)
    factoryOf(::TaskScreenModel)
}