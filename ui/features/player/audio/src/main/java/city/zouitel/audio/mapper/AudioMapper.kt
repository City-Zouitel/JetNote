package city.zouitel.audio.mapper

import city.zouitel.audio.model.Audio
import city.zouitel.domain.model.Audio as OutAudio

class  AudioMapper {

    fun fromDomain(audios: List<OutAudio?>) = audios.map { fromDomain(it) }

    fun toDomain(audio: Audio) = OutAudio(
        id = audio.id,
        uid = audio.uid,
        title = audio.title,
        path = audio.path,
        uri = audio.uri,
        size = audio.size,
        duration = audio.duration
    )


    fun fromDomain(audio: OutAudio?) = Audio(
        id = audio?.id ?: 0L,
        uid = audio?.uid ?: "",
        title = audio?.title ?: "",
        path = audio?.path ?: "",
        uri = audio?.uri ?: "",
        size = audio?.size ?: 0L,
        duration = audio?.duration ?: 0L
    )
}