package city.zouitel.audios

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AudioRepository(
    private val audioManager: AudioManager,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun loadAudioAmplitudes(
        localAudioPath: String
    ): List<Int> = withContext(ioDispatcher) {
        return@withContext audioManager.getAmplitudes(localAudioPath)
    }
}