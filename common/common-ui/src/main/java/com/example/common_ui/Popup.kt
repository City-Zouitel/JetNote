package com.example.common_ui

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.contentColorFor
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.example.common_ui.MaterialColors.Companion.ON_SURFACE
import com.example.common_ui.MaterialColors.Companion.SURFACE
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonHighlightAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.*

@Composable
fun PopupTip(
    message: String,
    window : @Composable (BalloonWindow) -> Unit
) {

    val getMaterialColors = MaterialColors().getMaterialColor
    val backgroundColor = getMaterialColors(ON_SURFACE)

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
                color = getMaterialColors.invoke(SURFACE)
            )
        },
        modifier = Modifier
    ) {
        window.invoke(it)
    }

}