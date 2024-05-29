package city.zouitel.recoder.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.logic.getRecPath
import city.zouitel.recoder.model.Audio
import city.zouitel.recoder.screenmodel.MediaRecordScreenModel
import city.zouitel.recoder.screenmodel.RecorderScreenModel
import kotlin.time.DurationUnit

data class RecorderScreen(
    val id: String,
    val onAction: () -> Unit
): Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val path = id getRecPath context

        val mediaRecordModel = getScreenModel<MediaRecordScreenModel>()
        val recorderModel = getScreenModel<RecorderScreenModel>()

        val uiState = recorderModel.uiState
        val record = mediaRecordModel.buildMediaRecord(id)

        if (uiState.isRecording) {
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
                            path = path,
                            uri = path.toUri().toString(),
                            duration = recorderModel.time.toLong(DurationUnit.SECONDS)
                        )
                    )
                    recorderModel.stop()
                }
                onAction.invoke()
            },
            confirmButton = {},
            text = {
                RecordController(
                    recorderModel = recorderModel,
                    mediaRecorder = record,
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
                                    path = path,
                                    uri = path.toUri().toString(),
                                    duration = recorderModel.time.toLong(DurationUnit.SECONDS)
                                )
                            )
                            recorderModel.stop()
                        }
                        onAction.invoke()
                    }
                )
            }
        )
    }
}