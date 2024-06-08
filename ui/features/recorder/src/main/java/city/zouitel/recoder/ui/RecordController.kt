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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import city.zouitel.recoder.screenmodel.RecorderScreenModel
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.CommonIcons.MIC_ICON_36
import city.zouitel.systemDesign.CommonIcons.PAUSE_CIRCLE_FILLED_ICON_36
import city.zouitel.systemDesign.CommonIcons.PLAY_CIRCLE_FILLED_ICON_36
import city.zouitel.systemDesign.CommonIcons.STOP_CIRCLE_ICON_36

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecordController(
    recorderModel: RecorderScreenModel,
    mediaRecorder: MediaRecorder,
    onStart: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
) {

    val uiState by remember(recorderModel, recorderModel::uiState).collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RecordTimer(uiState.seconds,uiState.minutes,uiState.hours)

        Row {
            CommonRow(
                modifier = Modifier.fillMaxWidth()
            ) {

                AnimatedVisibility(
                    visible = !uiState.isRecording,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    Icon(
                        painter = painterResource(id = MIC_ICON_36),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onStart()
                            recorderModel.updateRecordState(true)
                        }
                    )
                }

                AnimatedVisibility(
                    visible = uiState.isRecording,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    if (uiState.isPause) {
                        Icon(
                            painter = painterResource(id = PLAY_CIRCLE_FILLED_ICON_36),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                mediaRecorder.resume()
                                recorderModel.updatePausingState(false)
                                onStart()
                            }
                        )

                    } else {
                        Icon(
                            painter = painterResource(id = PAUSE_CIRCLE_FILLED_ICON_36),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                mediaRecorder.pause()
                                recorderModel.updatePausingState(true)
                                onPause()
                            }
                        )
                    }
                }

                AnimatedVisibility(
                    visible = uiState.isRecording,
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