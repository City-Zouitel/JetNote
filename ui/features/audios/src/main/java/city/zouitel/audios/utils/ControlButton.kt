package city.zouitel.audios.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.Icons

@Composable
internal fun ControlButton(isPlaying: MutableState<Boolean>) {
        Icon(
            painter = painterResource(
                id = if (isPlaying.value) Icons.PAUSE_CIRCLE_FILLED_ICON else Icons.PLAY_CIRCLE_FILLED_ICON
            ),
            null,
            modifier = Modifier
                .padding(start = 0.dp)
                .clickable {
                    isPlaying.value = !isPlaying.value
                }
        )

}