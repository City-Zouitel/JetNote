package city.zouitel.media.mapper

import city.zouitel.media.model.Media as InMedia
import city.zouitel.domain.model.Media as OutMedia

class MediaMapper {

    fun fromDomain(medias: List<OutMedia>) = medias.map { fromDomain(it) }

    fun toDomain(media: InMedia) = OutMedia(
        id = media.id,
        isVideo = media.isVideo,
        path = media.path
    )

    fun fromDomain(media: OutMedia) = InMedia(
        id = media.id,
        isVideo = media.isVideo,
        path = media.path
    )
}