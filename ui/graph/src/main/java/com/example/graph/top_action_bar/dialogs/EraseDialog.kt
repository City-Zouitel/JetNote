package com.example.graph.top_action_bar.dialogs

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.DataStoreVM
import com.example.common_ui.MaterialColors.Companion.SURFACE_TINT
import com.example.graph.getMaterialColor
import com.example.graph.sound

@Composable
fun EraseDialog(
    dataStoreVM: DataStoreVM = hiltViewModel(),
    dialogState: MutableState<Boolean>,
    confirmation : () -> Unit,
) {
    val ctx = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        confirmButton = {
            ClickableText(
                text = AnnotatedString("Confirm"),
                style = TextStyle(
                    color = getMaterialColor(SURFACE_TINT),
                    fontSize = 17.sp
                ),
                onClick = {
                    sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    confirmation.invoke()
                    dialogState.value = false
                }
            )
        },
        dismissButton = {
            ClickableText(
                text = AnnotatedString("Cansel"),
                style = TextStyle(
                    color = getMaterialColor(SURFACE_TINT),
                    fontSize = 17.sp
                ),
                onClick = {
                    sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    dialogState.value = false
                }
            )
        },
        title = {
            Text(text = "Empty Trash?")
        },
        text = {
            Text(text = "All notes is trash will be permanently deleted.")
        }
    )
}