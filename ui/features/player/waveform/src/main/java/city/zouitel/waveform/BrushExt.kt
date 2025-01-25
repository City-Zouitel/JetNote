package city.zouitel.waveform

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

@Composable
fun Brush.Companion.infiniteHorizontalGradient(
    infiniteTransition: InfiniteTransition = rememberInfiniteTransition(),
    animation: DurationBasedAnimationSpec<Float> = tween(2000, easing = LinearEasing),
    colors: List<Color>,
    width: Float
): Brush {
    val offset by getInfiniteOffset(infiniteTransition, animation, width)
    return horizontalGradient(colors, offset, offset + width, TileMode.Mirror)
}

@Composable
fun Brush.Companion.infiniteVerticalGradient(
    infiniteTransition: InfiniteTransition = rememberInfiniteTransition(),
    animation: DurationBasedAnimationSpec<Float> = tween(2000, easing = LinearEasing),
    colors: List<Color>,
    width: Float
): Brush {
    val offset by getInfiniteOffset(infiniteTransition, animation, width)
    return verticalGradient(colors, offset, offset + width, TileMode.Mirror)
}

@Composable
fun Brush.Companion.infiniteLinearGradient(
    infiniteTransition: InfiniteTransition = rememberInfiniteTransition(),
    animation: DurationBasedAnimationSpec<Float> = tween(2000, easing = LinearEasing),
    colors: List<Color>,
    width: Float
): Brush {
    val offset by getInfiniteOffset(infiniteTransition, animation, width)
    return linearGradient(colors, Offset(offset, offset), Offset(offset + width, offset + width), TileMode.Mirror)
}

@Composable
private fun getInfiniteOffset(
    infiniteTransition: InfiniteTransition,
    animation: DurationBasedAnimationSpec<Float>,
    width: Float
) : State<Float> {
    return infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = width * 2,
        animationSpec = infiniteRepeatable(
            animation = animation,
            repeatMode = RepeatMode.Restart
        )
    )
}