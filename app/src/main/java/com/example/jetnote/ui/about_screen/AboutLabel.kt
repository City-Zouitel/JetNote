package com.example.jetnote.ui.about_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jetnote.R
import com.example.jetnote.cons.APP_NAME
import com.example.jetnote.cons.ON_SURFACE
import com.example.jetnote.fp.getMaterialColor


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
            modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillBounds
        )
        Text(
            text = APP_NAME,
            fontSize = 55.sp,
            color = getMaterialColor(ON_SURFACE)
        )
    }
}