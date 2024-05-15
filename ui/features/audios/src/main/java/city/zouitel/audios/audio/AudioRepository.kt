package city.zouitel.audios.audio

import city.zouitel.audios.model.Audio
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AudioRepository(
    private val localMediaDataSource: LocalMediaDataSource,
    private val audioManager: AudioManager,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun loadAudioFiles(query: String): List<Audio> = withContext(ioDispatcher) {
        return@withContext localMediaDataSource.loadAudioFiles(query)
    }

    suspend fun loadAudioByContentId(id: Long): Audio? = withContext(ioDispatcher) {
        return@withContext localMediaDataSource.loadAudioById(id)
    }

    suspend fun loadAudioAmplitudes(
        localAudio: String
    ): List<Int> = withContext(ioDispatcher) {
        return@withContext audioManager.getAmplitudes(localAudio)
    }

}