package city.zouitel.domain.usecase

import city.zouitel.domain.model.Data
import city.zouitel.domain.repository.DataRepository

sealed class DataUseCase {

    class AddData(
        private val repository: DataRepository
    ): DataUseCase() {
        suspend operator fun invoke(data: Data) = repository.addData(data)
    }

    class EditData(
        private val repository: DataRepository
    ): DataUseCase() {
        suspend operator fun invoke(data: Data) = repository.editData(data)
    }

    class DeleteData(
        private val repository: DataRepository
    ): DataUseCase() {
        suspend operator fun invoke(data: Data) = repository.deleteData(data)
    }

    class DeleteAllTrashedData(
        private val repository: DataRepository
    ) : DataUseCase() {
        suspend operator fun invoke() = repository.deleteAllTrashedData()
    }
}
