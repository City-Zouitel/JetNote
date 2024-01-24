package city.zouitel.widget.di

import city.zouitel.widget.ui.WidgetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val widgetKoinModule = module {
    viewModel { WidgetViewModel(get()) }
}