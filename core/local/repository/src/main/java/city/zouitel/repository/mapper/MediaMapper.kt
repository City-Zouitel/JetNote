package city.zouitel.repository.mapper

import city.zouitel.repository.model.Media as InMedia
import city.zouitel.domain.model.Media as OutMedia

class MediaMapper {

    fun toDomain(medias: List<InMedia>) = medias.map { toDomain(it) }

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