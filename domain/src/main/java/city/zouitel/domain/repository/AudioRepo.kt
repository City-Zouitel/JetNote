package city.zouitel.domain.repository

import city.zouitel.domain.model.Audio
import kotlinx.coroutines.flow.Flow

interface AudioRepo {

    val getAllAudios: Flow<List<Audio>>

    suspend fun addAudio(audio: Audio)

    suspend fun updateAudio(audio: Audio)

    suspend fun deleteAudio(audio: Audio)
}