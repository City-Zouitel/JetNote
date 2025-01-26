package city.zouitel.recoder.screenmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.recoder.mapper.AudioMapper
import city.zouitel.recoder.model.Audio
import city.zouitel.recoder.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class RecorderScreenModel(
    private val insert: AudioUseCase.Insert,
    private val audioMapper: AudioMapper,
) : ScreenModel {

    var time: Duration by mutableStateOf(Duration.ZERO)

    private lateinit var timer: Timer

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState()
    )
    internal val uiState: StateFlow<UiState> = _uiState
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            UiState()
        )

    fun start() {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time += 1.seconds
            updateTimeStates()
        }
        _uiState.value = uiState.value.copy(isPlaying = true)
    }

    private fun updateTimeStates() {
        time.toComponents { hours, minutes, seconds, _ ->
            _uiState.value = _uiState.value.copy(
                seconds = seconds.pad(),
                minutes = minutes.pad(),
                hours = hours.toInt().pad()
            )
        }
    }

    private fun Int.pad(): String {
        return this.toString().padStart(2, '0')
    }

    fun pause() {
        timer.cancel()
        _uiState.value = _uiState.value.copy(isPlaying = false)
    }

    fun stop() {
        pause()
        time = Duration.ZERO
        updateTimeStates()
    }

    fun onRecordingComplete(id: String, audio: Audio) {
        screenModelScope.launch(Dispatchers.IO) {
            insert.invoke(audioMapper.toDomain(audio))

        }
    }

    fun updateRecordState(value: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isRecording = value)
        }
    }

    fun updatePausingState(value: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isPause = value)
        }
    }
}