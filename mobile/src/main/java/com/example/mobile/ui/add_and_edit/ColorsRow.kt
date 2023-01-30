package com.example.mobile.ui.add_and_edit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.datastore.DataStore
import com.example.mobile.cons.KEY_CLICK
import com.example.mobile.cons.SURFACE
import com.example.mobile.fp.getMaterialColor
import com.example.mobile.ui.settings_screen.makeSound

@Composable
fun ColorsRow(
    colorState: MutableState<Int>,
    colors: Array<Color>
    ) {

    val currentColor = remember { mutableStateOf(Color.White) }
    val ctx = LocalContext.current
    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(getMaterialColor(SURFACE)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        items(items = colors){
            Spacer(modifier = Modifier.width(5.dp))
            Canvas(
                modifier = Modifier.size(40.dp)
                    .clickable {
                        currentColor.value = it
                        colorState.value = it.toArgb()
                        Unit.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
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



