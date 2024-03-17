package city.zouitel.screens.about_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.screens.R
import city.zouitel.systemDesign.Cons.APP_NAME
import coil.compose.AsyncImage

@Composable
fun AboutLabel() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        AsyncImage(
            model = R.drawable.mat,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .blur(
                    radius = 4.dp,
                    edgeTreatment = BlurredEdgeTreatment.Rectangle
                )
        )
        Text(
            text = APP_NAME,
            fontSize = 60.sp,
            color = Color.White
        )
    }
}