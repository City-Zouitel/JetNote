package city.zouitel.database.mapper

import city.zouitel.database.mapper.base.Mapper
import city.zouitel.repository.model.Audio as OutAudio
import city.zouitel.database.model.AudioEntity as InAudio

class AudioMapper: Mapper.Base<InAudio, OutAudio> {
    override fun toLocal(data: OutAudio): InAudio = with(data) {
        InAudio(id, title, path, uri, size, duration)
    }

    override fun readOnly(data: InAudio): OutAudio = with(data) {
        OutAudio(id, title, path, uri, size, duration)
    }
}