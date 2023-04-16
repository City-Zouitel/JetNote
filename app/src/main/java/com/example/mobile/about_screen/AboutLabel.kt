package com.example.mobile.about_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common_ui.Cons.APP_NAME
import com.example.common_ui.MatColors.Companion.ON_SURFACE
import com.example.mobile.getMaterialColor


@Composable
fun AboutLabel() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        GlideImage(
            com.example.common_ui.R.drawable.mat,
            contentDescription = "",
        contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .blur(
                    radius = 3.dp,
                    edgeTreatment = BlurredEdgeTreatment.Rectangle
                ),
        )
        Text(
            text = APP_NAME,
            fontSize = 60.sp,
            color = getMaterialColor(ON_SURFACE)
        )
    }
}