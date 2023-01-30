package com.example.mobile.ui.about_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mobile.R
import com.example.mobile.cons.APP_NAME
import com.example.mobile.cons.ON_SURFACE
import com.example.mobile.fp.getMaterialColor


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AboutLabel() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        GlideImage(
            R.drawable.mat,
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