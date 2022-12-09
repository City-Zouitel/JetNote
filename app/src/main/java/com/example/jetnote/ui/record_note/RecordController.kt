package com.example.jetnote.ui.record_note

import android.annotation.SuppressLint
import android.media.MediaRecorder
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.jetnote.cons.*
import com.example.jetnote.icons.MIC_ICON_36
import com.example.jetnote.icons.PAUSE_CIRCLE_FILLED_ICON_36
import com.example.jetnote.icons.PLAY_CIRCLE_FILLED_ICON_36
import com.example.jetnote.icons.STOP_CIRCLE_ICON_36
import com.example.jetnote.ui.AdaptingRow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecordController(
    isRecording: MutableState<Boolean>,
    isPause: MutableState<Boolean>,
    dialogState: MutableState<Boolean>,
    mediaRecorder:MediaRecorder,
    seconds: String,
    minutes: String,
    hours: String,
    onStart: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
) {
    Column(
        Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RecordTimer(seconds,minutes,hours)

        Row {
            AdaptingRow(
                modifier = Modifier.fillMaxWidth()
            ) {

                AnimatedVisibility(visible = !isRecording.value, enter = scaleIn(), exit = scaleOut()) {
                        Icon(
                            painter = painterResource(id = MIC_ICON_36),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isRecording.value = true
                                onStart()
                            }
                        )
                }

                AnimatedVisibility(visible = isRecording.value, enter = scaleIn(), exit = scaleOut()) {
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

                AnimatedVisibility(visible = isRecording.value, enter = scaleIn(), exit = scaleOut()) {
                        Icon(
                            painter = painterResource(id = STOP_CIRCLE_ICON_36),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                mediaRecorder.stop()
                                mediaRecorder.reset()
                                mediaRecorder.release()
                                isRecording.value = false
                                dialogState.value = false
                                onStop()
                            }
                        )
                }
            }
        }
    }
}