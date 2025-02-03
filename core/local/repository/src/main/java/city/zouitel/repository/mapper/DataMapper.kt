package city.zouitel.repository.mapper

import city.zouitel.repository.model.Data
import city.zouitel.domain.model.Data as OutData

/**
 * Mapper class responsible for converting between [Data] (repository layer representation) and
 * [OutData] (domain layer representation) objects.
 */
class DataMapper {
    /**
     * Converts a list of [Data] entities to a list of corresponding domain [Data] models.
     *
     * This function iterates through a list of [Data] entities and applies the [toDomain(Data)]
     * conversion function to each entity, resulting in a new list containing the domain
     * representations of those Datas.
     *
     * @param Datas The list of [Data] entities to convert.
     * @return A list of [Data] domain models, where each model corresponds to a
     *         converted entity from the input list.
     *
     * @see toDomain(Data)
     */
    fun toDomain(data: Data) = OutData(
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
     * Converts an [OutData] (domain model) to a [Data] (repository model).
     *
     * This function maps the properties of an [OutData] to a new [Data] instance.
     * It's typically used when transferring data from the domain layer to the
     * data/presentation layer.
     *
     * @param Data The [OutData] object to convert.
     * @return A new [Data] object with properties copied from the input [OutData].
     */
    fun fromDomain(data: OutData) = Data(
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