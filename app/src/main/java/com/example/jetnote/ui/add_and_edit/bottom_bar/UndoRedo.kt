package com.example.jetnote.ui.add_and_edit.bottom_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jetnote.cons.KEY_CLICK
import com.example.jetnote.icons.REDO_ICON
import com.example.jetnote.cons.SURFACE_VARIANT
import com.example.jetnote.ds.DataStore
import com.example.jetnote.fp.getMaterialColor
import com.example.jetnote.icons.UNDO_ICON
import com.example.jetnote.ui.settings_screen.makeSound

@Composable
fun UndoRedo(
    titleFieldState: MutableState<String?>,
    descriptionFieldState: MutableState<String?>,
    isTitleFieldSelected : MutableState<Boolean>,
    isDescriptionFieldSelected : MutableState<Boolean>,
) {
    val titleStack = remember { mutableStateListOf<String>() }
    val descriptionStack = remember { mutableStateListOf<String>() }

    val ctx = LocalContext.current
    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false)

    Icon(
        painter = painterResource(id = UNDO_ICON),
        contentDescription = null,
        tint = contentColorFor(backgroundColor = getMaterialColor(SURFACE_VARIANT)),
        modifier = Modifier
            .size(20.dp)
            .clickable {
                Unit.makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)

                if (isTitleFieldSelected.value) {
                    if (titleFieldState.value?.isNotEmpty() == true) {
                        titleStack += titleFieldState.value!!
                            .split(' ')
                            .takeLast(1)
                    }
                    titleFieldState.value = if (!titleFieldState.value?.contains(' ')!!) {
                        ""
                    } else {
                        titleFieldState.value!!.substringBeforeLast(' ')
                    }

                }

                //
                if (isDescriptionFieldSelected.value) {

                    if (descriptionFieldState.value?.isNotEmpty() == true) {
                        descriptionStack += descriptionFieldState.value!!
                            .split(' ')
                            .takeLast(1)
                    }
                    descriptionFieldState.value =
                        if (!descriptionFieldState.value?.contains(' ')!!) {
                            ""
                        } else {
                            descriptionFieldState.value!!.substringBeforeLast(' ')
                        }
                }

            }
    )

    Icon(
        painter = painterResource(id = REDO_ICON),
        contentDescription = null,
        tint = contentColorFor(backgroundColor = getMaterialColor(SURFACE_VARIANT)),
        modifier = Modifier
            .size(20.dp)
            .clickable {
                Unit.makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)

                if (isTitleFieldSelected.value) {
                    if (titleStack.isNotEmpty()) {
                        titleFieldState.value += " " + titleStack.last()
                        titleStack -= titleStack.last()
                    }
                }
                if (isDescriptionFieldSelected.value) {
                    if (descriptionStack.isNotEmpty()) {
                        descriptionFieldState.value += " " + descriptionStack.last()
                        descriptionStack -= descriptionStack.last()
                    }
                }

            }
    )
}