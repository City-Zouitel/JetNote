package city.zouitel.recoder.ui

import android.annotation.SuppressLint
import android.media.MediaRecorder
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.recoder.model.Audio
import city.zouitel.recoder.viewmodel.RecorderScreenModel
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.Icons.MIC_ICON_36
import city.zouitel.systemDesign.Icons.PAUSE_CIRCLE_FILLED_ICON_36
import city.zouitel.systemDesign.Icons.PLAY_CIRCLE_FILLED_ICON_36
import city.zouitel.systemDesign.Icons.STOP_CIRCLE_ICON_36

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecordController(
    isRecording: MutableState<Boolean>,
    isPause: MutableState<Boolean>,
    dialogState: MutableState<Boolean>,
    mediaRecorder: MediaRecorder,
    recorderModel: RecorderScreenModel,
    seconds: String,
    minutes: String,
    hours: String,
    onStart: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
) {
    val navigator = LocalNavigator.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RecordTimer(seconds, minutes, hours)

        Row {
            CommonRow(
                modifier = Modifier.fillMaxWidth()
            ) {

                AnimatedVisibility(
                    visible = !isRecording.value,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    Icon(
                        painter = painterResource(id = MIC_ICON_36),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onStart()
                            isRecording.value = true
                        }
                    )
                }

                AnimatedVisibility(
                    visible = isRecording.value,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    if (isPause.value) {
                        Icon(
                            painter = painterResource(id = PLAY_CIRCLE_FILLED_ICON_36),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                mediaRecorder.resume()
                                isPause.value = false
                                onStart()
                            }
                        )

                    } else {
                        Icon(
                            painter = painterResource(id = PAUSE_CIRCLE_FILLED_ICON_36),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                mediaRecorder.pause()
                                isPause.value = true
                                onPause()
                            }
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isRecording.value,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    Icon(
                        painter = painterResource(id = STOP_CIRCLE_ICON_36),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onStop()
                        },
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}