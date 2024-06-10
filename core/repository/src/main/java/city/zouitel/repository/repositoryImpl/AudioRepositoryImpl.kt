package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.Audio
import city.zouitel.domain.model.Audio as OutAudio
import city.zouitel.domain.repository.AudioRepository
import city.zouitel.repository.datasource.AudioDataSource
import city.zouitel.repository.mapper.AudioMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AudioRepositoryImpl(
    private val dataSource: AudioDataSource,
    private val mapper: AudioMapper
): AudioRepository {
    override val getAllAudios: Flow<List<OutAudio>>
        get() = dataSource.getAllAudios.map { list ->
           list.map { audio ->
               mapper.toDomain(audio)
           }
        }

    override suspend fun addAudio(audio: OutAudio) {
        dataSource.addAudio(mapper.toRepository(audio))
    }

    override suspend fun updateAudio(audio: Audio) {
        dataSource.updateAudio(mapper.toRepository(audio))
    }

    override suspend fun deleteAudio(audio: OutAudio) {
        dataSource.deleteAudio(mapper.toRepository(audio))
    }
}