package com.example.note.bottom_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.DataStoreVM
import com.example.common_ui.Icons.REDO_ICON
import com.example.common_ui.Icons.UNDO_ICON
import com.example.common_ui.MaterialColors
import com.example.common_ui.MaterialColors.Companion.SURFACE_VARIANT
import com.example.common_ui.SoundEffect

@Composable
fun UndoRedo(
    dataStoreVM: DataStoreVM = hiltViewModel(),
    titleFieldState: MutableState<String?>,
    descriptionFieldState: MutableState<String?>,
    isTitleFieldSelected : MutableState<Boolean>,
    isDescriptionFieldSelected : MutableState<Boolean>,
) {
    val titleStack = remember { mutableStateListOf<String>() }
    val descriptionStack = remember { mutableStateListOf<String>() }

    val ctx = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val getMatColor = MaterialColors().getMaterialColor
    val sound = SoundEffect()

    Icon(
        painter = painterResource(id = UNDO_ICON),
        contentDescription = null,
        tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
        modifier = Modifier
            .size(20.dp)
            .clickable {
                sound.makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)

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
        tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
        modifier = Modifier
            .size(20.dp)
            .clickable {
                sound.makeSound(ctx, KEY_CLICK, thereIsSoundEffect.value)

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