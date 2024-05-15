package city.zouitel.recoder.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.AudioUseCase
import city.zouitel.domain.usecase.NoteAndAudioUseCase
import city.zouitel.recoder.mapper.AudioMapper
import city.zouitel.recoder.mapper.NoteAndAudioMapper
import city.zouitel.recoder.model.Audio
import city.zouitel.recoder.model.NoteAndAudio as InNoteAndAudio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class RecorderScreenModel(
    private val addAudio: AudioUseCase.AddAudio,
    private val addNoteAndAudio: NoteAndAudioUseCase.AddNoteAndAudio,
    private val audioMapper: AudioMapper,
    private val noteAndAudioMapper: NoteAndAudioMapper
) : ScreenModel {

    var time: Duration by mutableStateOf(Duration.ZERO)

    private lateinit var timer: Timer

    var seconds by mutableStateOf("00")
    var minutes by mutableStateOf("00")
    var hours by mutableStateOf("00")
    private var isPlaying by mutableStateOf(false)

    fun start() {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time += 1.seconds
            updateTimeStates()
        }
        isPlaying = true
    }

    private fun updateTimeStates() {
        time.toComponents { hours, minutes, seconds, _ ->
            this@RecorderScreenModel.seconds = seconds.pad()
            this@RecorderScreenModel.minutes = minutes.pad()
            this@RecorderScreenModel.hours = hours.toInt().pad()
        }
    }

    private fun Int.pad(): String {
        return this.toString().padStart(2, '0')
    }

    fun pause() {
        timer.cancel()
        isPlaying = false
    }

    fun stop() {
        pause()
        time = Duration.ZERO
        updateTimeStates()
    }

    fun onRecordingComplete(id: String, audio: Audio) {
        screenModelScope.launch(Dispatchers.IO) {
            addAudio.invoke(audioMapper.toDomain(audio))
            addNoteAndAudio.invoke(
                noteAndAudioMapper.toDomain(
                    InNoteAndAudio(noteUid = id, audioId = audio.id)
                )
            )
        }
    }
}