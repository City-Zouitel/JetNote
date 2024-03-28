package city.zouitel.screens.top_action_bar.dialogs

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.screens.sound
import org.koin.androidx.compose.koinViewModel

@Composable
fun EraseDialog(
    dataStoreVM: DataStoreVM = koinViewModel(),
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
                    color = MaterialTheme.colorScheme.surfaceTint,
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
                text = AnnotatedString("Cancel"),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 17.sp
                ),
                onClick = {
                    sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    dialogState.value = false
                }
            )
        },
        title = {
            Text(text = "Empty Notes?")
        },
        text = {
            Text(text = "All notes in trash will be permanently deleted.")
        }
    )
}