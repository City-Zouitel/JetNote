package com.example.jetnote.ui.record_note

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
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
        val numberTransitionSpec: AnimatedContentScope<String>.() -> ContentTransform = {
            slideInVertically(
                initialOffsetY = { it }
            ) + fadeIn() with slideOutVertically(
                targetOffsetY = { -it }
            ) + fadeOut() using SizeTransform(
                false
            )
        }

        CompositionLocalProvider(LocalTextStyle provides TextStyle(fontSize = 50.sp)) {
            AnimatedContent(targetState = hours, transitionSpec = numberTransitionSpec) {
                Text(text = it)
            }
            Text(text = ":")
            AnimatedContent(targetState = minutes, transitionSpec = numberTransitionSpec) {
                Text(text = it)
            }
            Text(text = ":")
            AnimatedContent(targetState = seconds, transitionSpec = numberTransitionSpec) {
                Text(text = it)
            }
        }
    }

}