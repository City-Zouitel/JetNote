package city.zouitel.repository.datasource

import city.zouitel.repository.model.Audio
import kotlinx.coroutines.flow.Flow

interface AudioDataSource {

    val getAllAudios: Flow<List<Audio>>

    suspend fun addAudio(audio: Audio)

    suspend fun updateAudio(audio: Audio)

    suspend fun deleteAudio(audio: Audio)
}