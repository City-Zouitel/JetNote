package city.zouitel.domain.usecase

import city.zouitel.domain.model.Data
import city.zouitel.domain.repository.DataRepository

sealed class DataUseCase {

    class AddData (
        private val repository: DataRepository
    ): DataUseCase() {
        suspend operator fun invoke(data: Data) = repository.addData(data)
    }

    class EditData /*@Inject*/ constructor(
        private val repository: DataRepository
    ): DataUseCase() {
        suspend operator fun invoke(data: Data) = repository.editData(data)
    }

    class DeleteData /*@Inject*/ constructor(
        private val repository: DataRepository
    ): DataUseCase() {
        suspend operator fun invoke(data: Data) = repository.deleteData(data)
    }

    class DeleteAllTrashedData /*@Inject*/ constructor(
        private val repository: DataRepository
    ) : DataUseCase() {
        suspend operator fun invoke() = repository.deleteAllTrashedData()
    }
}
