package city.zouitel.note.ui

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
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.SoundEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun ColorsRow(
    dataStoreVM: DataStoreVM = koinViewModel(),
    colorState: MutableState<Int>,
    colors: Array<Color>
    ) {

    val currentColor = remember { mutableStateOf(Color.White) }
    val ctx = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

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
                        sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                        currentColor.value = it
                        colorState.value = it.toArgb()
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



