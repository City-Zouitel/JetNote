package city.zouitel.database.mapper

import city.zouitel.database.model.Media
import city.zouitel.repository.model.Media as OutMedia

/**
 * A mapper class responsible for converting between repository [Media] and database [OutMedia].
 * This class provides methods for mapping lists of media and individual medias between the two types.
 */
class MediaMapper {

    /**
     * Maps a list of Medias objects to a list of their repository representations.
     *
     * @param medias The list of Reminder objects to map.
     * @return A list containing the repository representations of the given Medias
     * that are mapped from Media to OutMedia.
     */
    fun toRepo(medias: List<Media>) = medias.map { toRepo(it) }

    /**
     * Maps a Media object to an OutMedia object.
     *
     * This function converts a Media object, which is typically used internally,
     * to an OutMedia object, which is likely used for external communication or data transfer.
     *
     * @param media The input Media object to be mapped.
     * @return An OutMedia object representing the mapped Media.
     */
    fun toRepo(media: Media) = OutMedia(
        id = media.id,
        uid = media.uid,
        isVideo = media.isVideo,
        uri = media.uri
    )

    /**
     * Maps an OutMedia data object from the repository layer to a Media data object for the repository layer.
     *
     * @param media The OutMedia object to map.
     * @return A Media object representing the same data.
     */
    fun fromRepo(media: OutMedia) = Media(
        id = media.id,
        uid = media.uid,
        isVideo = media.isVideo,
        uri = media.uri
    )
}