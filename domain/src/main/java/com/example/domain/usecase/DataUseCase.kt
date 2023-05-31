package com.example.domain.usecase

import com.example.domain.model.Data
import com.example.domain.repository.DataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class DataUseCase {

    class AddData @Inject constructor (
        private val repository: DataRepository
    ): DataUseCase() {
        suspend operator fun invoke(data: Data) = repository.addData(data)
    }

    class EditData @Inject constructor(
        private val repository: DataRepository
    ): DataUseCase() {
        suspend operator fun invoke(data: Data) = repository.editData(data)
    }

    class DeleteData @Inject constructor(
        private val repository: DataRepository
    ): DataUseCase() {
        suspend operator fun invoke(data: Data) = repository.deleteData(data)
    }

    class DeleteAllTrashedData @Inject constructor(
        private val repository: DataRepository
    ) : DataUseCase() {
        suspend operator fun invoke() = repository.deleteAllTrashedData()
    }
}
