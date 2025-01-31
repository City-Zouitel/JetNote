package city.zouitel.repository.mapper

import city.zouitel.repository.model.NoteAndTag
import city.zouitel.domain.model.NoteAndTag as OutNoteAndTag

/**
 * Mapper class responsible for converting between [NoteAndTag] (repository layer representation) and
 * [OutNoteAndTag] (domain layer representation) objects.
 */
class NoteAndTagMapper {
    /**
     * Converts a list of [NoteAndTag] entities to a list of corresponding domain [NoteAndTag] models.
     *
     * This function iterates through a list of [NoteAndTag] entities and applies the [toDomain(NoteAndTag)]
     * conversion function to each entity, resulting in a new list containing the domain
     * representations of those NoteAndTags.
     *
     * @param NoteAndTags The list of [NoteAndTag] entities to convert.
     * @return A list of [NoteAndTag] domain models, where each model corresponds to a
     *         converted entity from the input list.
     *
     * @see toDomain(NoteAndTag)
     */
    fun toDomain(notesAndTag: List<NoteAndTag>) = notesAndTag.map { toDomain(it) }

    /**
     * Converts a [NoteAndTag] entity repository model to an [OutNoteAndTag] domain model.
     *
     * This function maps the properties of a [NoteAndTag] object to a corresponding [OutNoteAndTag] object,
     * effectively transforming a data-layer representation of a NoteAndTag into a domain-layer
     * representation. The mapping includes the NoteAndTag's ID, UID, item description, and completion status.
     *
     * @param NoteAndTag The [NoteAndTag] entity to convert.
     * @return An [OutNoteAndTag] domain model representing the provided NoteAndTag.
     */
    private fun toDomain(noteAndTag: NoteAndTag) = OutNoteAndTag(
        uid = noteAndTag.uid,
        id = noteAndTag.id
    )

    /**
     * Converts an [OutNoteAndTag] (domain model) to a [NoteAndTag] (repository model).
     *
     * This function maps the properties of an [OutNoteAndTag] to a new [NoteAndTag] instance.
     * It's typically used when transferring data from the domain layer to the
     * data/presentation layer.
     *
     * @param NoteAndTag The [OutNoteAndTag] object to convert.
     * @return A new [NoteAndTag] object with properties copied from the input [OutNoteAndTag].
     */
    fun fromDomain(noteAndTag: OutNoteAndTag) = NoteAndTag(
        uid = noteAndTag.uid,
        id = noteAndTag.id
    )
}