package city.zouitel.systemDesign

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.baha.url.preview.BuildConfig
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