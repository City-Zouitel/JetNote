package city.zouitel.repository.mapper

import city.zouitel.repository.mapper.base.Mapper
import city.zouitel.repository.model.Audio as InAudio
import city.zouitel.domain.model.Audio as OutAudio

class AudioMapper: Mapper.Base<InAudio, OutAudio> {
    override fun toRepository(data: OutAudio): InAudio = with(data) {
        InAudio(id, title, path, uri, size, duration)
    }

    override fun toDomain(data: InAudio): OutAudio = with(data) {
        OutAudio(id, title, path, uri, size, duration)
    }
}