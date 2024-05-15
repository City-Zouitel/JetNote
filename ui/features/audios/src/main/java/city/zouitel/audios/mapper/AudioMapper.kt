package city.zouitel.audios.mapper

import city.zouitel.audios.mapper.base.Mapper
import city.zouitel.audios.model.Audio as InAudio
import city.zouitel.domain.model.Audio as OutAudio

class AudioMapper: Mapper.Base<InAudio, OutAudio> {
    override fun toView(data: OutAudio): InAudio = with(data) {
        InAudio(id, title, path, uri, size, duration)
    }

    override fun toDomain(data: InAudio): OutAudio = with(data) {
        OutAudio(id, title, path, uri, size, duration)
    }
}