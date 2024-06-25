package city.zouitel.repository.mapper

import city.zouitel.repository.model.Audio as InAudio
import city.zouitel.domain.model.Audio as OutAudio

class  AudioMapper {

    fun toDomain(audios: List<InAudio>) = audios.map { toDomain(it) }

    fun toDomain(audio: InAudio) = OutAudio(
        id = audio.id,
        title = audio.title,
        path = audio.path,
        uri = audio.uri,
        size = audio.size,
        duration = audio.duration
    )

    fun fromDomain(audio: OutAudio) = InAudio(
        id = audio.id,
        title = audio.title,
        path = audio.path,
        uri = audio.uri,
        size = audio.size,
        duration = audio.duration
    )
}