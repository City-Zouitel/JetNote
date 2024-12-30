package city.zouitel.repository.mapper

import city.zouitel.repository.model.Media
import city.zouitel.domain.model.Media as OutMedia

/**
 * A mapper class responsible for converting between [Media] (repo module) and [OutMedia] (domain layer) objects.
 */
class MediaMapper {

    /**
     * Maps a list of Reminder data objects to a list of domain models.
     *
     * @param medias The list of Media data objects to map.
     * @return A list of domain models representing the given medias.
     */
    fun toDomain(medias: List<Media>) = medias.map { toDomain(it) }

    /**
     * Maps a Media data object to an OutMedia domain object.
     *
     * @param media The Media data object to map.
     * @return An OutMedia domain object representing the same media.
     */
    fun toDomain(media: Media) = OutMedia(
        id = media.id,
        uid = media.uid,
        isVideo = media.isVideo,
        uri = media.uri
    )

    /**
     * Maps an OutMedia (domain model) to a Media (data model).
     *
     * @param media The domain model representation of a media.
     * @return The data model representation of the media.
     */
    fun fromDomain(media: OutMedia) = Media(
        id = media.id,
        uid = media.uid,
        isVideo = media.isVideo,
        uri = media.uri
    )
}