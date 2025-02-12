package city.zouitel.audio.ui.record

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.domain.utils.Action
import city.zouitel.systemDesign.CommonIcons.PAUSE_ICON_24
import city.zouitel.systemDesign.CommonIcons.PLAY_ICON_24
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.CommonSwipeItem
import java.text.SimpleDateFormat
import java.util.Locale

class RecordsScreen(val uid: String): Screen {
    @Composable
    override fun Content() {
        val playerModel = getScreenModel<RecordScreenModel>()
        LaunchedEffect(true) {
            playerModel.initController()
            playerModel.initRecorders(uid)
        }

        val uiState by remember(playerModel, playerModel::uiState).collectAsState()
        val recorders by remember(playerModel, playerModel::observeByUid).collectAsState()

        LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
            playerModel.forOnes()
        }

        Column {
            for (item in recorders) {
                CommonSwipeItem(
                    enableRightDirection = false,
                    onSwipeLeft = {
                        playerModel.sendAction(Action.Delete(item))
                    }
                ) {
                    Card(
                        modifier = Modifier.padding(10.dp),
                        shape = RoundedCornerShape(17.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(.6f, .6f, .6f, .5f)
                        )
                    ) {
                        Row {
                            CommonRow(
                                modifier = Modifier
                                    .padding(start = 5.dp, end = 5.dp)
                                    .height(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(if (item.path == uiState.currentPath && uiState.isPlaying) PAUSE_ICON_24 else PLAY_ICON_24),
                                    null,
                                    modifier = Modifier
                                        .clickable {
                                            playerModel.playbackItemState(item)
                                        },
                                    tint = Color.Companion.White
                                )

                                LinearProgressIndicator(
                                    progress = { if (item.path == uiState.currentPath) uiState.progress else 0f },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(7.dp),
                                    trackColor = Color.White,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    strokeCap = StrokeCap.Round
                                )

                                Text(
                                    modifier = Modifier.padding(end = 7.dp),
                                    text = formatLong(item.duration),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    private fun formatLong(value: Long): String {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(value)
    }
}