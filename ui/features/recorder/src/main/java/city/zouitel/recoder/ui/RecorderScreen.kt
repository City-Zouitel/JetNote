package city.zouitel.recoder.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.recoder.viewmodel.MediaRecordScreenModel
import city.zouitel.recoder.viewmodel.RecorderScreenModel
import city.zouitel.systemDesign.Cons.REC_DIR
import city.zouitel.systemDesign.Cons.MP3
import java.io.File

data class RecorderScreen(val id: String/*, val dialogState: MutableState<Boolean>*/): Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val mediaRecordModel = getScreenModel<MediaRecordScreenModel>()
        val recorderModel = getScreenModel<RecorderScreenModel>()

        val isRecording = remember { mutableStateOf(false) }
        val isPause = remember { mutableStateOf(false) }


        val path = File(context.filesDir.path + "/$REC_DIR/" + id + "." + MP3)

        val record = mediaRecordModel.buildMediaRecord(id)

        if (isRecording.value) {
            record.start()
        }

        // TODO: optimization!
        RecordController(
            isRecording = isRecording,
            isPause = isPause,
//            dialogState = dialogState,
            mediaRecorder = record,
            recorderScreenModel = recorderModel,
            seconds = recorderModel.seconds,
            minutes = recorderModel.minutes,
            hours = recorderModel.hours,
            onStart = { recorderModel.start() },
            onPause = { recorderModel.pause() },
            onStop = { recorderModel.stop() }
        )

//        AlertDialog(
//            onDismissRequest = {
//                runCatching {
//                    record.stop()
//                    record.reset()
//                    record.release()
//                    recorderModel.stop()
//                }
//                dialogState.value = false
//            },
//            confirmButton = {},
//            text = {
//                RecordController(
//                    isRecording = isRecording,
//                    isPause = isPause,
//                    dialogState = dialogState,
//                    mediaRecorder = record,
//                    seconds = recorderModel.seconds,
//                    minutes = recorderModel.minutes,
//                    hours = recorderModel.hours,
//                    onStart = { recorderModel.start() },
//                    onPause = { recorderModel.pause() },
//                    onStop = { recorderModel.stop() }
//                )
//            }
//        )
    }
}

