package city.zouitel.recoder.ui

import android.annotation.SuppressLint
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import city.zouitel.recoder.viewmodel.MediaRecordVM
import city.zouitel.recoder.viewmodel.RecorderVM
import city.zouitel.systemDesign.Cons.AUDIOS
import city.zouitel.systemDesign.Cons.MP3
import org.koin.androidx.compose.koinViewModel
import java.io.File
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "CoroutineCreationDuringComposition"
)
@Composable
fun RecordingNote(
    noteMediaRecordVM: MediaRecordVM = koinViewModel(),
    dialogState: MutableState<Boolean>,
    uid: String?
) {

    val ctx = LocalContext.current
    val isRecording = remember { mutableStateOf(false) }
    val isPause = remember { mutableStateOf(false) }

    val recorderVm = viewModel(RecorderVM::class.java)

    val path = File(
        ctx.filesDir.path + "/$AUDIOS/" + uid + "." + MP3
    )
    val record = noteMediaRecordVM.buildMediaRecord(path.path)

    if (isRecording.value) {
        record.start()
    }

    AlertDialog(
        onDismissRequest = {
            runCatching {
                record.stop()
                record.reset()
                record.release()
                recorderVm.stop()
            }
            dialogState.value = false
        },
        confirmButton = {},
        text = {
            RecordController(
                isRecording = isRecording,
                isPause = isPause,
                dialogState = dialogState,
                mediaRecorder = record,
                seconds = recorderVm.seconds,
                minutes = recorderVm.minutes,
                hours = recorderVm.hours,
                onStart = { recorderVm.start() },
                onPause = { recorderVm.pause() },
                onStop = { recorderVm.stop() }
            )
        }
    )
}