package city.zouitel.recoder.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.recoder.viewmodel.MediaRecordScreenModel
import city.zouitel.recoder.viewmodel.RecorderScreenModel
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.Cons
import city.zouitel.systemDesign.Cons.REC_DIR
import city.zouitel.systemDesign.Cons.MP3
import city.zouitel.systemDesign.Icons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

//data class RecorderScreen(val id: String): Screen {
//    @RequiresApi(Build.VERSION_CODES.S)
//    @Composable
//    override fun Content() {
//
//        val context = LocalContext.current
//        val navigator = LocalNavigator.currentOrThrow
//
//        val mediaFile = arrayOf(
//            context.filesDir.path,
//            Cons.REC_DIR,
//            id + "." + Cons.MP3
//        ).joinToString("/")
//
//        val tempMedia = "/storage/emulated/0/Download/987694586541321.mp3"
//
//
//        val scope = rememberCoroutineScope()
//        val mediaRecordModel = getScreenModel<MediaRecordScreenModel>()
//        val recorderModel = getScreenModel<RecorderScreenModel>()
//
//        val isRecording = remember { mutableStateOf(false) }
//        val isPause = remember { mutableStateOf(false) }
//
//
//        val record = mediaRecordModel.buildMediaRecord(mediaFile)
//
//        if (isRecording.value) {
//            runCatching {
//                record.prepare()
//            }.onSuccess {
//                record.start()
//            }
//        }
//
//        Column(
//            Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.surface),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            RecordTimer(recorderModel.seconds, recorderModel.minutes, recorderModel.hours)
//
//            Row {
//                CommonRow(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//
//                    AnimatedVisibility(visible = !isRecording.value, enter = scaleIn(), exit = scaleOut()) {
//                        Icon(
//                            painter = painterResource(id = Icons.MIC_ICON_36),
//                            contentDescription = null,
//                            modifier = Modifier.clickable {
//                                recorderModel.start()
//                                isRecording.value = true
//                            }
//                        )
//                    }
//
//                    AnimatedVisibility(visible = isRecording.value, enter = scaleIn(), exit = scaleOut()) {
//                        if (isPause.value) {
//                            Icon(
//                                painter = painterResource(id = Icons.PLAY_CIRCLE_FILLED_ICON_36),
//                                contentDescription = null,
//                                modifier = Modifier.clickable {
//                                    record.resume()
//                                    isPause.value = false
//                                    recorderModel.start()
//                                }
//                            )
//
//                        } else {
//                            Icon(
//                                painter = painterResource(id = Icons.PAUSE_CIRCLE_FILLED_ICON_36),
//                                contentDescription = null,
//                                modifier = Modifier.clickable {
//                                    record.pause()
//                                    isPause.value = true
//                                    recorderModel.pause()
//                                }
//                            )
//                        }
//                    }
//
//                    AnimatedVisibility(visible = isRecording.value, enter = scaleIn(), exit = scaleOut()) {
//                        Icon(
//                            painter = painterResource(id = Icons.STOP_CIRCLE_ICON_36),
//                            contentDescription = null,
//                            modifier = Modifier.clickable {
//                                scope.launch(Dispatchers.IO) {
//                                    isRecording.value = false
//                                    record.apply {
//                                        stop()
//                                        reset()
//                                        release()
//                                    }
//                                    recorderModel.stop()
//                                    isRecording.value = false
//                                    navigator.pop()
//                                }
//                            },
//                            tint = MaterialTheme.colorScheme.error
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun RecorderDialog(
    id: String,
    dialogState: MutableState<Boolean>
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow

    val path = File(context.filesDir, "$REC_DIR/$id.$MP3")

    val mediaRecordModel = navigator.rememberNavigatorScreenModel { MediaRecordScreenModel(context, path.path) }
    val recorderModel = navigator.rememberNavigatorScreenModel { RecorderScreenModel() }

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
                    recorderScreenModel = recorderModel,
                    seconds = recorderModel.seconds,
                    minutes = recorderModel.minutes,
                    hours = recorderModel.hours,
                    onStart = { recorderModel.start() },
                    onPause = { recorderModel.pause() },
                    onStop = { recorderModel.stop() }
                )
            }
        )
}
