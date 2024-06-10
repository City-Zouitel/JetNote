package city.zouitel.audios.audio

import city.zouitel.audios.model.Audio
import city.zouitel.audios.state.SingleAudioUiState
import java.util.concurrent.TimeUnit
import kotlin.math.log2
import kotlin.math.pow

val Long.formatAsAudioDuration: String get() = TimeUnit.MILLISECONDS.let { timeUnit ->
    String.format(
        "%d:%d",
        timeUnit.toMinutes(this),
        timeUnit.toSeconds(this).let { if(it < 100) it else it / 10 }
    )
}

val Long.formatAsFileSize: String
    get() = log2(if (this != 0L) toDouble() else 1.0).toInt().div(10).let {
        val precision = when (it) {
            0 -> 0; 1 -> 1; else -> 2
        }
        val prefix = arrayOf("", "K", "M", "G", "T", "P", "E", "Z", "Y")
        String.format("%.${precision}f ${prefix[it]}B", toDouble() / 2.0.pow(it * 10.0))
    }

internal fun Audio.toUiState(onClick: () -> Unit) = SingleAudioUiState(
    id = id,
    displayName = title,
    size = duration.formatAsAudioDuration + " | " + size.formatAsFileSize,
    onClick = onClick,
)