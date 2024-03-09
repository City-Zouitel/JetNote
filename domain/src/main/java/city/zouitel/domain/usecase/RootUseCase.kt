package city.zouitel.domain.usecase

import city.zouitel.domain.repository.RootRepository

sealed class RootUseCase {

    class RootUseCase(
        private val repository: RootRepository
    ) {

        operator fun invoke() = repository.isDeviceRooted
    }
}