package com.example.domain.usecase

import com.example.domain.repository.WidgetRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class WidgetUseCase {

    class GetAllWidgetMainEntityById @Inject constructor(
        repository: WidgetRepository
    ): WidgetUseCase() {

    }
}
