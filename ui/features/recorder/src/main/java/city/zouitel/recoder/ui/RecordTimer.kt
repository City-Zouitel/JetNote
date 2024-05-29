package city.zouitel.recoder.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecordTimer(
    seconds: String,
    minutes: String,
    hours: String,
) {
    Row(
        modifier = Modifier
            .height(100.dp)
    ) {
        val numberTransitionSpec: AnimatedContentTransitionScope<String>.() -> ContentTransform = {
            (slideInVertically(
                initialOffsetY = { it }
            ) + fadeIn()).togetherWith(slideOutVertically(
                targetOffsetY = { -it }
            ) + fadeOut()) using SizeTransform(
                false
            )
        }

        CompositionLocalProvider(LocalTextStyle provides TextStyle(fontSize = 50.sp, color = MaterialTheme.colorScheme.onSurface)) {
            AnimatedContent(targetState = hours, transitionSpec = numberTransitionSpec, label = "") {
                Text(text = it)
            }
            Text(text = ":")
            AnimatedContent(targetState = minutes, transitionSpec = numberTransitionSpec,
                label = ""
            ) {
                Text(text = it)
            }
            Text(text = ":")
            AnimatedContent(targetState = seconds, transitionSpec = numberTransitionSpec,
                label = ""
            ) {
                Text(text = it)
            }
        }
    }
}