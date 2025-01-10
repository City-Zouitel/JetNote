package city.zouitel.database.mapper

import city.zouitel.database.model.Link
import city.zouitel.repository.model.Link as OutLink

/**
 * Mapper class responsible for converting between [Link] (domain model) and [OutLink] (repository model).
 *
 * This class provides methods to transform a list of tasks or a single link
 * between the domain and repository representations.
 */
class LinkMapper {

    /**
     * Maps a list of Link objects to a list of their repository representations.
     *
     * @param links The list of Links objects to map.
     * @return A list containing the repository representations of the given links
     * that are mapped from Link to OutLink.
     */
    fun toRepo(links: List<Link>) = links.map { toRepo(it) }

    /**
     * Maps a Task object to an OutLink object.
     *
     * This function converts a Task object, which is typically used internally,
     * to an OutLink object, which is likely used for external communication or data transfer.
     *
     * @param link The input Link object to be mapped.
     * @return An OutLink object representing the mapped Link.
     */
    fun toRepo(link: Link) = OutLink(
        id = link.id,
        uid = link.uid,
        url = link.url,
        title = link.title,
        description = link.description,
        image = link.image,
        icon = link.icon
    )

    /**
     * Maps an OutLink data object from the repository layer to a Link data object for the repository layer.
     *
     * @param link The OutLink object to map.
     * @return A Link object representing the same data.
     */
    fun fromRepo(link: OutLink) = Link(
        id = link.id,
        uid = link.uid,
        url = link.url,
        title = link.title,
        description = link.description,
        image = link.image,
        icon = link.icon
    )
}