package city.zouitel.database.mapper

import city.zouitel.database.model.Tag
import city.zouitel.repository.model.Tag as OutTag

/**
 * A mapper class responsible for converting between repository [Tag] and database [OutTag].
 * This class provides methods for mapping lists of Tag and individual Tags between the two types.
 */
class TagMapper {
    /**
     * Maps a list of Tag objects to a list of their repository representations.
     *
     * @param Tags The list of Tags objects to map.
     * @return A list containing the repository representations of the given Tags
     * that are mapped from Tag to OutTag.
     */
    fun toRepo(tags: List<Tag>) = tags.map { toRepo(it) }

    /**
     * Maps a Task object to an OutTag object.
     *
     * This function converts a Task object, which is typically used internally,
     * to an OutTag object, which is likely used for external communication or data transfer.
     *
     * @param Tag The input Tag object to be mapped.
     * @return An OutTag object representing the mapped Tag.
     */
    fun toRepo(tag: Tag) = OutTag(
        id = tag.id,
        label = tag.label,
        color = tag.color
    )

    /**
     * Maps an OutTag data object from the repository layer to a Tag data object for the repository layer.
     *
     * @param Tag The OutTag object to map.
     * @return A Tag object representing the same data.
     */
    fun fromRepo(tag: OutTag) = Tag(
        id = tag.id,
        label = tag.label,
        color = tag.color
    )
}