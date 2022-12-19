package com.example.jetnote.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.Settings.Global.getString
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ComponentRegistry
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageDisplayed(
    media: MutableState<Uri?>?,
) {
    val ctx = LocalContext.current
    Box(
        modifier = Modifier.fillMaxWidth().shadow(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = AbsoluteRoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation()
        ) {
//            media?.let {
//                GlideImage(
//                    model = it.value,
//                    contentDescription = null
//                )
//            }
        }
    }
}