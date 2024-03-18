package city.zouitel.widget.di

import city.zouitel.widget.mapper.WidgetMapper
import city.zouitel.widget.viewmodel.WidgetScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val widgetKoinModule = module {
    factory {
        WidgetMapper(get(), get(), get(), get())
    }

    factory {
        WidgetScreenModel(get(), get())
    }
}