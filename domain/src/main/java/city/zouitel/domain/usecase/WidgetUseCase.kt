package city.zouitel.domain.usecase

import city.zouitel.domain.repository.WidgetRepository

sealed class WidgetUseCase {

    class GetAllWidgetMainEntityById(
        private val repository: WidgetRepository
    ): WidgetUseCase() {
        operator fun invoke() = repository.getAllWidgetMainEntityById
    }
}
