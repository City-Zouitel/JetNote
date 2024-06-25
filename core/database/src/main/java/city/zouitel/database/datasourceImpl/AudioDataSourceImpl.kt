package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.AudioDao
import city.zouitel.database.mapper.AudioMapper
import city.zouitel.repository.datasource.AudioDataSource
import city.zouitel.repository.model.Audio
import city.zouitel.repository.model.Audio as OutAudio
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AudioDataSourceImpl(
    private val dao: AudioDao,
    private val mapper: AudioMapper
): AudioDataSource {
    override val getAllAudios: Flow<List<OutAudio>>
        get() = dao.getAllAudios().map { audios -> mapper.toRepo(audios) }

    override suspend fun addAudio(audio: OutAudio) {
        dao.addAudio(mapper.fromRepo(audio))
    }

    override suspend fun updateAudio(audio: Audio) {
        dao.updateAudio(mapper.fromRepo(audio))
    }

    override suspend fun deleteAudio(audio: OutAudio) {
        dao.deleteAudio(mapper.fromRepo(audio))
    }

}