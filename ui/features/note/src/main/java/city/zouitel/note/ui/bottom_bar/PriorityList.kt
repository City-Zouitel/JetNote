package city.zouitel.note.ui.bottom_bar

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import city.zouitel.systemDesign.Cons.IMPORTANT
import city.zouitel.systemDesign.Cons.LOW
import city.zouitel.systemDesign.Cons.NON
import city.zouitel.systemDesign.Cons.NORMAL
import city.zouitel.systemDesign.Cons.URGENT
import city.zouitel.systemDesign.Icons.CIRCLE_ICON

@Deprecated("dispensed with!")
@Composable
internal fun PriorityList(
    isShow: MutableState<Boolean>,
    notePriority: MutableState<String>
) {
    DropdownMenu(
        expanded = isShow.value,
        onDismissRequest = {
            isShow.value = false
        },
        properties = PopupProperties(
            focusable = true
        ),
        modifier = Modifier
    ) {
        DropdownMenuItem(
            text = { Text(text = "Urgent", fontSize = 18.sp) },
            leadingIcon = {
                Icon(painterResource(CIRCLE_ICON), null, tint = Color.Red)
            },
            onClick = {
                notePriority.value = URGENT
                isShow.value = false
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Important", fontSize = 18.sp) },
            leadingIcon = {
                Icon(painterResource(CIRCLE_ICON), null, tint = Color.Yellow)
            },
            onClick = {
                notePriority.value = IMPORTANT
                isShow.value = false
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Normal", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(CIRCLE_ICON), null,
                tint = Color.Green) },
            onClick = {
                notePriority.value = NORMAL
                isShow.value = false
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Low", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(CIRCLE_ICON), null,
                tint = Color.Cyan) },
            onClick = {
                notePriority.value = LOW
                isShow.value = false
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Non", fontSize = 18.sp) },
            leadingIcon = { Icon(painterResource(CIRCLE_ICON), null,
                tint = Color.Gray) },
            onClick = {
                notePriority.value = NON
                isShow.value = false
            }
        )
    }
}