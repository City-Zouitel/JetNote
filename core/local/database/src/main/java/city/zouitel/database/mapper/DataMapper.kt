package city.zouitel.database.mapper

import city.zouitel.database.model.Data
import city.zouitel.repository.model.Data as OutData

/**
 * Mapper class responsible for converting between [Data] (domain model) and [OutData] (repository model).
 *
 * This class provides methods to transform a list of tasks or a single Data
 * between the domain and repository representations.
 */
class DataMapper {
    /**
     * Maps a Task object to an OutData object.
     *
     * This function converts a Task object, which is typically used internally,
     * to an OutData object, which is likely used for external communication or data transfer.
     *
     * @param Data The input Data object to be mapped.
     * @return An OutData object representing the mapped Data.
     */
    fun toRepo(data: Data) = OutData(
        uid = data.uid,
        title = data.title,
        description = data.description,
        priority = data.priority,
        background = data.background,
        textColor = data.textColor,
        date = data.date,
        archived = data.archived
    )

    /**
     * Maps an OutData data object from the repository layer to a Data data object for the repository layer.
     *
     * @param Data The OutData object to map.
     * @return A Data object representing the same data.
     */
    fun fromRepo(data: OutData) = Data(
        uid = data.uid,
        title = data.title,
        description = data.description,
        priority = data.priority,
        background = data.background,
        textColor = data.textColor,
        date = data.date,
        archived = data.archived
    )
}