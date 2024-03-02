package city.zouitel.tags.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.listOfBackgroundColors
import city.zouitel.tags.viewmodel.TagScreenModel
import org.koin.androidx.compose.koinViewModel
import city.zouitel.tags.model.Tag as InTag

@Composable
internal fun DialogColors(
    tagModel: TagScreenModel,
    dialogState:MutableState<Boolean>,
    idState: MutableState<Long>,
    labelState: MutableState<String>,
    colorState: MutableState<Int>
) {
    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        confirmButton = {},
        modifier = Modifier.padding(30.dp),
        text = {
            LazyVerticalGrid(columns = GridCells.Fixed(7), content = {
                items(listOfBackgroundColors) {
                    Canvas(
                        modifier = Modifier
                            .size(37.dp)
                            .padding(2.dp)
                            .clickable {
                                /*colorState.value = it.toArgb()*/
                                tagModel
                                    .updateTag(
                                        InTag(
                                            id = idState.value,
                                            label = labelState.value,
                                            color = it.toArgb()
                                        )
                                    )
                                    .invokeOnCompletion {
                                        dialogState.value = false
                                        colorState.value = 0x0000
                                        labelState.value = ""
                                        idState.value = -1L
                                    }
                            }
                    ) {
                        drawArc(
                            color = it,
                            startAngle = 1f,
                            sweepAngle = 360f,
                            useCenter = true,
                            style = Fill
                        )
                    }
                }
            })
        }
    )
}
