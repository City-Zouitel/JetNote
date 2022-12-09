package com.example.jetnote.ui.record_note

import android.annotation.SuppressLint
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetnote.cons.AUDIO_FILE
import com.example.jetnote.cons.MP3
import com.example.jetnote.vm.MediaRecordVM
import com.example.jetnote.vm.RecordVM
import java.io.File
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "CoroutineCreationDuringComposition"
)
@Composable
fun RecordingNote(
    noteMediaRecordVM: MediaRecordVM = hiltViewModel(),
    dialogState: MutableState<Boolean>,
    uid: String?
) {

    val ctx = LocalContext.current
    val isRecording = remember { mutableStateOf(false) }
    val isPause = remember { mutableStateOf(false) }

    val recordVm = viewModel(RecordVM::class.java)

    val path = File(
        ctx.filesDir.path + "/$AUDIO_FILE/" + uid + "." + MP3
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
                recordVm.stop()
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
                seconds = recordVm.seconds,
                minutes = recordVm.minutes,
                hours = recordVm.hours,
                onStart = { recordVm.start() },
                onPause = { recordVm.pause() },
                onStop = { recordVm.stop() }
            )
        }
    )
}