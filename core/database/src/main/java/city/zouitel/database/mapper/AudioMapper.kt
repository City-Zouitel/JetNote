package city.zouitel.database.mapper

import city.zouitel.repository.model.Audio as OutAudio
import city.zouitel.database.model.AudioEntity as InAudio

class  AudioMapper {

    fun toRepo(audios: List<InAudio>) = audios.map { toRepo(it) }

    fun toRepo(audio: InAudio) = OutAudio(
        id = audio.id,
        title = audio.title,
        path = audio.path,
        uri = audio.uri,
        size = audio.size,
        duration = audio.duration
    )

    fun fromRepo(audio: OutAudio) = InAudio(
        id = audio.id,
        title = audio.title,
        path = audio.path,
        uri = audio.uri,
        size = audio.size,
        duration = audio.duration
    )
}