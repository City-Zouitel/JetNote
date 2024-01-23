package city.zouitel.domain.usecase

import city.zouitel.domain.repository.WidgetRepository

//@Singleton
sealed class WidgetUseCase {

    class GetAllWidgetMainEntityById /*@Inject*/ constructor(
        private val repository: WidgetRepository
    ): WidgetUseCase() {
        operator fun invoke() = repository.getAllWidgetMainEntityById
    }
}
