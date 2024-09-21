package city.zouitel.systemDesign

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonHighlightAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.*

@Composable
fun CommonPopupTip(
    message: String,
    window : @Composable (BalloonWindow) -> Unit
) {
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPosition(0.5f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(5)
        setCornerRadius(8f)
        setBackgroundColor(backgroundColor)
        setBalloonAnimation(BalloonAnimation.ELASTIC)
        setBalloonHighlightAnimation(BalloonHighlightAnimation.SHAKE)
        setAutoDismissDuration(5000)
    }

    Balloon(
        builder = builder,
        balloonContent = {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.surface
            )
        },
        modifier = Modifier
    ) {
        window.invoke(it)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommonPopupTip(
    color: (Int) -> Unit,
    window : @Composable (BalloonWindow) -> Unit
) {
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPosition(0.5f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(5)
        setCornerRadius(8f)
        setBackgroundColor(backgroundColor)
        setBalloonAnimation(BalloonAnimation.ELASTIC)
        setBalloonHighlightAnimation(BalloonHighlightAnimation.NONE)
        setAutoDismissDuration(7000)
    }

    Balloon(
        builder = builder,
        balloonContent = {
            LazyVerticalGrid(
                GridCells.Fixed(5),
                modifier = Modifier.size(100.dp),
            ) {
                items(listOfBackgroundColors) {
                    Spacer(modifier = Modifier.width(3.dp))

                    Canvas(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                color.invoke(it.toArgb())
                            }
                    ) {
                        drawArc(
                            color = if (it == Color.Transparent) Color.Gray else it,
                            startAngle = 1f,
                            sweepAngle = 360f,
                            useCenter = true,
                            style =
//                            if (getColorOfPriority(uiState.priority) == it.toArgb()) {
//                                Stroke(width = 5f, cap = StrokeCap.Round)
//                            } else {
                                Fill
//                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(3.dp))
                }
            }
        },
        modifier = Modifier
    ) {
        window.invoke(it)
    }
}