package city.zouitel.audio.ui.record

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.audio.mapper.RecordMapper
import city.zouitel.audio.model.Record
import city.zouitel.audio.player.PlaybackManager
import city.zouitel.audio.state.UiState
import city.zouitel.domain.usecase.RecordUseCase
import city.zouitel.domain.utils.Action
import city.zouitel.domain.utils.withAsync
import city.zouitel.logic.withFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@Suppress("DeferredResultUnused")
class RecordScreenModel(
    private val observeAll: RecordUseCase.ObserveAll,
    private val _observeByUid_: RecordUseCase.ObserveByUid,
    private val delete: RecordUseCase.Delete,
    private val player: PlaybackManager,
    private val mapper: RecordMapper
): ScreenModel {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    internal val uiState: StateFlow<UiState> = _uiState
        .withFlow(UiState())

    private val _observeByUid: MutableStateFlow<List<Record>> = MutableStateFlow(emptyList())
    val observeByUid: StateFlow<List<Record>> = _observeByUid
        .withFlow(emptyList())

    private var currentRecord = MutableStateFlow<Record?>(null)

    fun initRecorders(uid: String) {
        withAsync {
            _observeByUid_(uid).collect {
                _observeByUid.value = mapper.fromDomain(it)
            }
        }
    }

    fun forOnes() {
        withAsync {
            observeAll().collect {
                player.addRecords(mapper.fromDomain(it))
            }
        }
    }

    fun sendAction(act: Action) {
        when(act) {
            is Action.Delete<*> -> withAsync {
                val path = (act.data as Record).path

                delete(-1, path)
                player.removeRecord(act.data as Record)
                File(path).delete()
            }
            else -> throw Exception("Action not supported")
        }
    }

    fun initController() {
        player.initializeController()
    }

    fun playbackItemState(item: Record) {
        screenModelScope.launch {
            when {
                _uiState.value.isPlaying -> {
                    player.pauseItem(item)
                }
                else -> {
                    player.playItem(item)
                }
            }
            currentRecord.value = item
            launch { observePlaybackEvents() }
        }
    }

    private suspend fun observePlaybackEvents() {
        player.events.collectLatest {
            when (it) {
                is PlaybackManager.Event.PositionChanged -> updatePlaybackProgress(it.position)
                is PlaybackManager.Event.PlayingChanged -> updatePlayingState(it.isPlaying)
                is PlaybackManager.Event.IsLoading -> updateLoadingState(it.isLoading)
                is PlaybackManager.Event.CurrentPath -> updateCurrentPath(it.path)
            }
        }
    }

    private fun updatePlaybackProgress(position: Long) {
        val record = currentRecord.value ?: return
        _uiState.value = uiState.value.copy(progress = position.toFloat() / record.duration)
    }

    private fun updatePlayingState(isPlaying: Boolean) {
        _uiState.value = uiState.value.copy(isPlaying = isPlaying)
    }

    private fun updateLoadingState(isLoading: Boolean) {
        _uiState.value = uiState.value.copy(isLoading = isLoading)
    }

    private fun updateCurrentPath(path: String) {
        _uiState.value = uiState.value.copy(currentPath = path)
    }

    override fun onDispose() {
        super.onDispose()
        player.clearPlaylist()
    }
}