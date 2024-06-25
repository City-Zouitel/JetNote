package city.zouitel.audios.mapper

import city.zouitel.audios.mapper.base.Mapper
import city.zouitel.audios.model.Audio as InAudio
import city.zouitel.domain.model.Audio as OutAudio

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