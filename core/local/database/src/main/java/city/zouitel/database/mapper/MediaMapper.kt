package city.zouitel.database.mapper

import city.zouitel.repository.model.Media
import city.zouitel.database.model.MediaEntity as InMedia
import city.zouitel.repository.model.Media as OutMedia

class MediaMapper {

    fun toRepo(medias: List<InMedia>) = medias.map { toRepo(it) }

    fun toRepo(media: InMedia) = OutMedia(
        id = media.id,
        isVideo = media.isVideo,
        path = media.path
    )

    fun fromRepo(media: OutMedia) = InMedia(
        id = media.id,
        isVideo = media.isVideo,
        path = media.path
    )
}