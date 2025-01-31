package city.zouitel.repository.mapper

import city.zouitel.repository.model.Tag
import city.zouitel.domain.model.Tag as OutTag

/**
 * Mapper class responsible for converting between [Tag] (repository layer representation) and
 * [OutTag] (domain layer representation) objects.
 */
class TagMapper {

    /**
     * Converts a list of [Tag] entities to a list of corresponding domain [Tag] models.
     *
     * This function iterates through a list of [Tag] entities and applies the [toDomain(Tag)]
     * conversion function to each entity, resulting in a new list containing the domain
     * representations of those Tags.
     *
     * @param Tags The list of [Tag] entities to convert.
     * @return A list of [Tag] domain models, where each model corresponds to a
     *         converted entity from the input list.
     *
     * @see toDomain(Tag)
     */
    fun toDomain(tags: List<Tag>) = tags.map { toDomain(it) }

    /**
     * Converts a [Tag] entity repository model to an [OutTag] domain model.
     *
     * This function maps the properties of a [Tag] object to a corresponding [OutTag] object,
     * effectively transforming a data-layer representation of a Tag into a domain-layer
     * representation. The mapping includes the Tag's ID, UID, item description, and completion status.
     *
     * @param Tag The [Tag] entity to convert.
     * @return An [OutTag] domain model representing the provided Tag.
     */
    fun toDomain(tag: Tag) = OutTag(
        id = tag.id,
        label = tag.label,
        color = tag.color
    )

    /**
     * Converts an [OutTag] (domain model) to a [Tag] (repository model).
     *
     * This function maps the properties of an [OutTag] to a new [Tag] instance.
     * It's typically used when transferring data from the domain layer to the
     * data/presentation layer.
     *
     * @param Tag The [OutTag] object to convert.
     * @return A new [Tag] object with properties copied from the input [OutTag].
     */
    fun fromDomain(tag: OutTag) = Tag(
        id = tag.id,
        label = tag.label,
        color = tag.color
    )
}