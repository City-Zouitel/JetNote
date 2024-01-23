package city.zouitel.systemDesign

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
