package city.zouitel.widget.di

import city.zouitel.domain.usecase.WidgetUseCase
import city.zouitel.widget.ui.WidgetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val widgetKoinModule = module {
    viewModel { param ->
        WidgetViewModel(
            param.get<WidgetUseCase.GetAllWidgetMainEntityById>()
        )
    }
}