package city.zouitel.recoder.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.audio.player.PlaybackManager
import city.zouitel.domain.usecase.RecordUseCase
import city.zouitel.domain.utils.Action
import city.zouitel.domain.utils.withAsync
import city.zouitel.logic.withFlow
import city.zouitel.recoder.mapper.RecordMapper
import city.zouitel.recoder.model.Record
import city.zouitel.recoder.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Suppress("DeferredResultUnused")
class RecorderScreenModel(
    private val insert: RecordUseCase.Insert,
    private val repo: RecorderRepo,
    private val player: PlaybackManager,
    private val mapper: RecordMapper
): ScreenModel {
    var time: Duration by mutableStateOf(Duration.ZERO)

    private lateinit var timer: Timer

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    internal val uiState: StateFlow<UiState> = _uiState
        .withFlow(UiState())

    fun start(file: File) {
        withAsync {
            launch { repo.start(file) }
            launch {
                timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
                    time += 1.seconds
                    updateTimeStates()
                }
            }
            _uiState.value = _uiState.value.copy(isRecording = true)
        }
    }

    fun pause() {
        repo.pause()
        timer.cancel()
        _uiState.value = _uiState.value.copy(isPause = true)
    }

    fun resume() {
        repo.resume()
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time += 1.seconds
            updateTimeStates()
        }
        _uiState.value = _uiState.value.copy(isPause = false)
    }

    fun stop() {
        withAsync {
            launch { repo.stop() }
            launch {
                _uiState.value = _uiState.value.copy(isRecording = false)
            }
        }
    }

    fun reset() {
        timer.cancel()
        time = Duration.ZERO
        updateTimeStates()
    }

    fun sendAction(act: Action) {
        when (act) {
            is Action.Insert<*> -> withAsync {
                val record = act.data as Record
                insert(mapper.toDomain(record))
                player.addRecord(mapper.fromAudio(record))
            }
            else -> throw Exception("Action not supported")
        }
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

    override fun onDispose() {
        super.onDispose()
        stop()
    }
}