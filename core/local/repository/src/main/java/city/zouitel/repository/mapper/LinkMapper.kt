package city.zouitel.repository.mapper

import city.zouitel.repository.model.Link
import city.zouitel.domain.model.Link as OutLink

/**
 * Mapper class responsible for converting between [Link] (repository layer representation) and
 * [OutLink] (domain layer representation) objects.
 */
class LinkMapper {

    /**
     * Converts a list of [Link] entities to a list of corresponding domain [Link] models.
     *
     * This function iterates through a list of [Link] entities and applies the [toDomain(Link)]
     * conversion function to each entity, resulting in a new list containing the domain
     * representations of those Links.
     *
     * @param links The list of [Link] entities to convert.
     * @return A list of [Link] domain models, where each model corresponds to a
     *         converted entity from the input list.
     *
     * @see toDomain(Link)
     */
    fun toDomain(links: List<Link>) = links.map { toDomain(it) }

    /**
     * Converts a [Link] entity repository model to an [OutLink] domain model.
     *
     * This function maps the properties of a [Link] object to a corresponding [OutLink] object,
     * effectively transforming a data-layer representation of a Link into a domain-layer
     * representation. The mapping includes the Link's ID, UID, item description, and completion status.
     *
     * @param link The [Link] entity to convert.
     * @return An [OutLink] domain model representing the provided Link.
     */
    fun toDomain(link: Link) = OutLink(
        id = link.id,
        uid = link.uid,
        url = link.url,
        title = link.title,
        description = link.description,
        image = link.image,
        icon = link.icon
    )

    /**
     * Converts an [OutLink] (domain model) to a [Link] (repository model).
     *
     * This function maps the properties of an [OutLink] to a new [Link] instance.
     * It's typically used when transferring data from the domain layer to the
     * data/presentation layer.
     *
     * @param link The [OutLink] object to convert.
     * @return A new [Link] object with properties copied from the input [OutLink].
     */
    fun fromDomain(link: OutLink) = Link(
        id = link.id,
        uid = link.uid,
        url = link.url,
        title = link.title,
        description = link.description,
        image = link.image,
        icon = link.icon
    )
}