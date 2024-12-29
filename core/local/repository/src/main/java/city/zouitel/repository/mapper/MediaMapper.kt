package city.zouitel.repository.mapper

import city.zouitel.repository.model.Media
import city.zouitel.domain.model.Media as OutMedia

class MediaMapper {

    fun toDomain(medias: List<Media>) = medias.map { toDomain(it) }

    fun toDomain(media: Media) = OutMedia(
        id = media.id,
        uid = media.uid,
        isVideo = media.isVideo,
        uri = media.path
    )

    fun fromDomain(media: OutMedia) = Media(
        id = media.id,
        uid = media.uid,
        isVideo = media.isVideo,
        path = media.uri
    )
}