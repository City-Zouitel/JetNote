package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Audio
import city.zouitel.domain.model.Audio as OutAudio
import city.zouitel.domain.repository.AudioRepo
import city.zouitel.repository.datasource.AudioDataSource
import city.zouitel.repository.mapper.AudioMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AudioRepoImpl(
    private val dataSource: AudioDataSource,
    private val mapper: AudioMapper
): AudioRepo {
    override val getAllAudios: Flow<List<OutAudio>>
        get() = dataSource.getAllAudios.map { audios ->  mapper.toDomain(audios) }

    override suspend fun addAudio(audio: OutAudio) {
        dataSource.addAudio(mapper.fromDomain(audio))
    }

    override suspend fun updateAudio(audio: Audio) {
        dataSource.updateAudio(mapper.fromDomain(audio))
    }

    override suspend fun deleteAudio(audio: OutAudio) {
        dataSource.deleteAudio(mapper.fromDomain(audio))
    }
}