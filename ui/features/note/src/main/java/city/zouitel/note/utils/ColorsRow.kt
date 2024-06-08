package city.zouitel.note.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.SoundEffect

@Composable
fun ColorsRow(
    dataStoreModel: DataStoreScreenModel,
    colors: Array<Color>,
    onAction: (Int) -> Unit
) {

    val currentColor = remember { mutableStateOf(Color.White) }
    val context = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()

    val sound = SoundEffect()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        items(items = colors){
            Spacer(modifier = Modifier.width(5.dp))
            Canvas(
                modifier = Modifier
                    .size(27.dp)
                    .clickable {
                        sound.makeSound.invoke(context, KEY_CLICK, thereIsSoundEffect.value)
                        currentColor.value = it
                        onAction.invoke(it.toArgb())
                    }
            ){
                drawArc(color = it,
                    startAngle = 1f,
                    sweepAngle = 360f,
                    useCenter = true,
                    style =
                    if (currentColor.value == it) {
                        Stroke(width = 10f,cap = StrokeCap.Round)
                    } else {
                        Fill
                    }
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}



