package city.zouitel.database.mapper

import city.zouitel.database.model.Media
import city.zouitel.repository.model.Media as OutMedia

class MediaMapper {

    fun toRepo(medias: List<Media>) = medias.map { toRepo(it) }

    fun toRepo(media: Media) = OutMedia(
        id = media.id,
        uid = media.uid,
        isVideo = media.isVideo,
        path = media.path
    )

    fun fromRepo(media: OutMedia) = Media(
        id = media.id,
        uid = media.uid,
        isVideo = media.isVideo,
        path = media.path
    )
}