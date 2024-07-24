package city.zouitel.tags.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.listOfBackgroundColors
import city.zouitel.tags.ui.TagScreenModel
import city.zouitel.tags.model.Tag as InTag

@Composable
fun DialogColors(tagModel: TagScreenModel) {

    val uiState by remember(tagModel, tagModel::uiState).collectAsState()

    AlertDialog(
        onDismissRequest = { tagModel.updateColorDialogState(false) },
        confirmButton = {},
        modifier = Modifier.padding(7.dp),
        text = {
            LazyVerticalGrid(columns = GridCells.Fixed(7), content = {
                items(listOfBackgroundColors) {
                    Canvas(
                        modifier = Modifier
                            .size(37.dp)
                            .padding(2.dp)
                            .clickable {
                                tagModel
                                    .updateTag(
                                        InTag(
                                            id = uiState.currentId,
                                            label = uiState.currentLabel,
                                            color = it.toArgb()
                                        )
                                    )
                                    .invokeOnCompletion {
                                        tagModel
                                            .updateId()
                                            .updateColorDialogState()
                                            .updateColor()
                                            .updateLabel()
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
