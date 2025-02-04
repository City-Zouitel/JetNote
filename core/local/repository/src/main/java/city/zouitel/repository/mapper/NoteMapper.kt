package city.zouitel.repository.mapper

import city.zouitel.repository.model.Data
import city.zouitel.repository.model.Note
import city.zouitel.domain.model.Note as OutNote

/**
 * Mapper class responsible for converting [Note] data objects to [OutNote] domain objects.
 *
 * This class utilizes [DataMapper] and [TagMapper] to handle the mapping of nested data structures
 * within a [Note].
 *
 * @property dataMapper The [DataMapper] instance used for mapping the nested `data` field.
 * @property tagMapper The [TagMapper] instance used for mapping the list of `tags`.
 */
class NoteMapper(
    private val dataMapper: DataMapper,
    private val tagMapper: TagMapper
) {
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
    fun toDomain(notes: List<Note>) = notes.map { toDomain(it) }

    /**
     * Converts a [Data] entity repository model to an [OutData] domain model.
     *
     * This function maps the properties of a [Data] object to a corresponding [OutData] object,
     * effectively transforming a data-layer representation of a Data into a domain-layer
     * representation. The mapping includes the Data's ID, UID, item description, and completion status.
     *
     * @param Data The [Data] entity to convert.
     * @return An [OutData] domain model representing the provided Data.
     */
    fun toDomain(data: Note): OutNote = with(data){
        OutNote(
            data = dataMapper.toDomain(this.data),
            tags = tags.map { tagMapper.toDomain(it) }
        )
    }
}