package com.example.quick_note
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.Cons
import com.example.common_ui.MatColors
import com.example.common_ui.MatColors.Companion.SURFACE
import com.example.local.model.Note
import java.util.UUID
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Quick(
    quickNoteVM: QuickNoteVM = hiltViewModel(),
    action:() -> (Unit)
    ) {
    val descriptionState = remember { mutableStateOf("") }

    val matColors = MatColors().getMaterialColor
    val backgroundColor = matColors(SURFACE).toArgb()
    val backgroundColorState = rememberSaveable { mutableStateOf(backgroundColor) }
    val textColor = contentColorFor(matColors(SURFACE)).toArgb()
    val textColorState = rememberSaveable { mutableStateOf(textColor) }
    val priorityState = remember { mutableStateOf(Cons.URGENT) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
        ) {
            TextField(
                value = descriptionState.value,
                onValueChange = { descriptionState.value = it })
            Button(onClick = {
                quickNoteVM.addQuickNote(
                    Note(
                        description = descriptionState.value,
                        uid = UUID.randomUUID().toString(),
                        color = backgroundColorState.value,
                        textColor = textColorState.value,
                        priority = priorityState.value
                    )
                ).invokeOnCompletion {
                    action.invoke()
                }
            }) {
                Text(text = "Done")
            }
        }
    }
}