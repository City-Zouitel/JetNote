package com.example.mobile.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageDisplayed(
    media: ImageBitmap?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(20.dp),
        contentAlignment = Alignment.Center
    ){
        Card(
            shape = AbsoluteRoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation()
        ) {
            media?. let {
                Image(
                    bitmap = it,
                    null,
                    modifier = Modifier
                )
            }
        }
    }
}