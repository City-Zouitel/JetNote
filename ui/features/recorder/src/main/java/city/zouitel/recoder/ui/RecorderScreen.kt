package city.zouitel.recoder.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

        Recorder(
            mediaRecordModel = getScreenModel<MediaRecordScreenModel>(),
            recorderModel =getScreenModel<RecorderScreenModel>()
        )
    }

    @Composable
    private fun Recorder(
        mediaRecordModel: MediaRecordScreenModel,
        recorderModel: RecorderScreenModel
    ) {
        val context = LocalContext.current
        val path = id getRecPath context

        val uiState by remember(recorderModel, recorderModel::uiState).collectAsState()

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