package city.zouitel.media.mapper

import city.zouitel.domain.model.Media as OutMedia
import city.zouitel.media.model.Media as InMedia

class MediaMapper {

    fun fromDomain(medias: List<OutMedia>) = medias.map { fromDomain(it) }

    fun toDomain(media: InMedia) = OutMedia(
        id = media.id,
        uid = media.uid,
        isVideo = media.isVideo,
        uri = media.uri
    )

    fun fromDomain(media: OutMedia) = InMedia(
        id = media.id,
        uid = media.uid,
        isVideo = media.isVideo,
        uri = media.uri
    )
}