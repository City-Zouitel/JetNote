package city.zouitel.audios.media

import android.content.Context
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.systemDesign.Cons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class AudioListViewModel(
    private val context: Context,
    private val audioRepository: AudioRepository
) : ViewModel() {

    var navDestination: String? by mutableStateOf(null)
        private set

    var uiState: AudioListUiState by mutableStateOf(AudioListUiState())
        private set

    private var loadAudioJob: Job? = null

    var loadNotePath: String by mutableStateOf("")

    fun updateSearchQuery(query: String) {
        uiState = uiState.copy(searchQuery = query)
        loadAudioFiles(query)
    }

    init {
        loadAudioFiles()
    }
    fun updatePermissionsState(granted: Boolean) {
        uiState = uiState.copy(
//            permissionsState = when {
//                granted -> PermissionsState.Granted
//                else -> PermissionsState.Denied
//            }
        )
        if(granted) {
//            loadAudioFiles()
        }
    }

    private fun loadAudioFiles(query: String? = null) {
        loadAudioJob?.cancel()
        loadAudioJob = viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoadingAudios = true)
                val audioFiles = audioRepository.loadAudioFiles(query ?: uiState.searchQuery)
                    .map { it.toUiState { selectAudio(it) } }
                uiState = uiState.copy(audioFiles = audioFiles)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                uiState = uiState.copy(isLoadingAudios = false)
            }
        }
    }

    private fun selectAudio(localAudio: LocalAudio) {
        viewModelScope.launch(Dispatchers.IO) {
            //  navDestination = NavDestination.AudioWaveform.createRoute(localAudio.id)
        }
    }
    fun clearNavDestination() {
        navDestination = null
    }
}