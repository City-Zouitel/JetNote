package com.example.jetnote.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun ImageDisplayed(
    image : ImageBitmap?
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
            if (image != null) {
                Image(
                    bitmap = image,
                    null,
                    modifier = Modifier
                )
            }
        }
    }
}