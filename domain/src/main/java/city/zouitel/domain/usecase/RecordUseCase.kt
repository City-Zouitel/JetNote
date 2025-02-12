package city.zouitel.domain.usecase

import city.zouitel.domain.model.Record
import city.zouitel.domain.repository.RecordRepo

sealed class RecordUseCase {
    data class ObserveAll(private val repo: RecordRepo) {
        operator fun invoke() = repo.observeAll
    }

    data class ObserveByUid(private val repo: RecordRepo) {
        suspend operator fun invoke(uid: String) = repo.observeByUid(uid)
    }

    data class Insert(private val repo: RecordRepo) {
        suspend operator fun invoke(record: Record) = repo.insert(record)
    }

    data class Delete(private val repo: RecordRepo) {
        suspend operator fun invoke(id: Long, path: String) = repo.delete(id, path)
        suspend operator fun invoke(uid: String) = repo.delete(uid)
    }
}