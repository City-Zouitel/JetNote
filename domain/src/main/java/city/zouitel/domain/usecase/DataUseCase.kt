package city.zouitel.domain.usecase

import city.zouitel.domain.model.Data
import city.zouitel.domain.repository.DataRepository

/**
 * A sealed class representing various use cases for interacting with data.
 * Each use case is a data class that encapsulates a specific operation on a [DataRepository].
 *
 * This class utilizes the `suspend operator fun invoke(...)` pattern to provide a concise way
 * to execute each use case. This pattern makes it easy to call the use case as if it were a function.
 */
sealed class DataUseCase {
    /**
     * [Insert] is a use case responsible for inserting a [Data] entity into the data layer.
     *
     * It leverages a [DataRepository] to perform the actual insertion.
     *
     * @property repo The [DataRepository] instance used for data operations.
     */
    data class Insert(private val repo: DataRepository): DataUseCase() {
        suspend operator fun invoke(data: Data) = repo.insert(data)
    }

    /**
     * Use case for archiving a specific data item.
     *
     * This class encapsulates the logic for marking a data item as archived within the underlying
     * [DataRepository]. It provides a single, suspendable operator function ([invoke]) to perform
     * the archiving operation.
     *
     * @property repo The [DataRepository] instance used to interact with the data storage layer.
     * It should be pre-configured and injected into this use case.
     */
    data class Archive(private val repo: DataRepository): DataUseCase() {
        suspend operator fun invoke(uid: String) = repo.archive(uid)
    }

    /**
     * [Rollback] is a [DataUseCase] responsible for rolling back data changes associated with a specific user ID.
     *
     * It leverages a [DataRepository] to perform the actual rollback operation.
     *
     * @property repo The [DataRepository] instance used to interact with the data layer.
     */
    data class Rollback(private val repo: DataRepository): DataUseCase() {
        suspend operator fun invoke(uid: String) = repo.rollback(uid)
    }

    /**
     * Represents a use case for deleting data from the repository.
     *
     * This use case provides a suspendable operation to delete a data item identified by a unique ID.
     *
     * @property repo The [DataRepository] instance used to interact with the data layer.
     */
    data class Delete(private val repo: DataRepository): DataUseCase() {
        suspend operator fun invoke(uid: String) = repo.delete(uid)
    }

    /**
     * [Erase] is a [DataUseCase] responsible for erasing all data managed by the [DataRepository].
     *
     * This use case provides a single public function, [invoke], which triggers the data erasure process.
     * It delegates the actual data deletion to the provided [DataRepository].
     *
     * @property repo The [DataRepository] used to manage and erase data.
     */
    data class Erase(private val repo: DataRepository): DataUseCase() {
        suspend operator fun invoke() = repo.erase()
    }
}
