package city.zouitel.recoder.mapper

import city.zouitel.domain.model.Audio as OutAudio
import city.zouitel.recoder.model.Audio as InAudio

class  AudioMapper {

    fun fromDomain(audios: List<OutAudio>) = audios.map { fromDomain(it) }

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