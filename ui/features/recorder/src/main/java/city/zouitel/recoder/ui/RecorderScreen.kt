package city.zouitel.recoder.ui

import android.net.Uri
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.recoder.model.Audio
import city.zouitel.recoder.viewmodel.MediaRecordScreenModel
import city.zouitel.recoder.viewmodel.RecorderScreenModel
import city.zouitel.systemDesign.Cons.MP3
import city.zouitel.systemDesign.Cons.REC_DIR
import java.io.File
import kotlin.time.DurationUnit

data class RecorderScreen(
    val id:String, val dialogState: MutableState<Boolean>
): Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current

        val path = File(context.filesDir, "$REC_DIR/$id.$MP3")

        val mediaRecordModel = getScreenModel<MediaRecordScreenModel>()
        val recorderModel = getScreenModel<RecorderScreenModel>()

        val isRecording = remember { mutableStateOf(false) }
        val isPause = remember { mutableStateOf(false) }

        val record = mediaRecordModel.buildMediaRecord(id)

        if (isRecording.value) {
            record.start()
        }

        AlertDialog(
            onDismissRequest = {
                runCatching {
                    record.stop()
                    record.reset()
                    record.release()
                }.onSuccess {
                    recorderModel.onRecordingComplete(
                        id,
                        Audio(
                            path = path.path,
                            uri = path.path.toUri().toString(),
                            duration = recorderModel.time.toLong(DurationUnit.SECONDS)
                        )
                    )
                    recorderModel.stop()
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
                    recorderModel = recorderModel,
                    seconds = recorderModel.seconds,
                    minutes = recorderModel.minutes,
                    hours = recorderModel.hours,
                    onStart = { recorderModel.start() },
                    onPause = { recorderModel.pause() },
                    onStop = {
                        runCatching {
                            record.stop()
                            record.reset()
                            record.release()
                        }.onSuccess {
                            recorderModel.onRecordingComplete(
                                id,
                                Audio(
                                    path = path.path,
                                    uri = path.path.toUri().toString(),
                                    duration = recorderModel.time.toLong(DurationUnit.SECONDS)
                                )
                            )
                            recorderModel.stop()
                        }
                        dialogState.value = false
                    }
                )
            }
        )
    }
}