package city.zouitel.recoder.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import city.zouitel.systemDesign.CommonIcons.MIC_ICON_36
import city.zouitel.systemDesign.CommonIcons.PAUSE_CIRCLE_FILLED_ICON_36
import city.zouitel.systemDesign.CommonIcons.PLAY_CIRCLE_FILLED_ICON_36
import city.zouitel.systemDesign.CommonIcons.STOP_CIRCLE_ICON_36
import city.zouitel.systemDesign.CommonRow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecordController(
    recorderModel: RecorderScreenModel,
    onStart: () -> Unit = {},
    onPause: () -> Unit = {},
    onResume: () -> Unit = {},
    onStop: () -> Unit = {}
) {
    val uiState by remember(recorderModel, recorderModel::uiState).collectAsState()

    Row {
        CommonRow(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth()
        ) {

            AnimatedVisibility(
                visible = !uiState.isRecording,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                Icon(
                    painter = painterResource(id = MIC_ICON_36),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = onStart),
                    tint = MaterialTheme.colorScheme.onSurface
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
                        modifier = Modifier.clickable(onClick = onResume),
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                } else {
                    Icon(
                        painter = painterResource(id = PAUSE_CIRCLE_FILLED_ICON_36),
                        contentDescription = null,
                        modifier = Modifier.clickable(onClick = onPause),
                        tint = MaterialTheme.colorScheme.onSurface
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
                    modifier = Modifier.clickable(onClick = onStop),
                    tint = MaterialTheme.colorScheme.errorContainer
                )
            }
        }
    }
}