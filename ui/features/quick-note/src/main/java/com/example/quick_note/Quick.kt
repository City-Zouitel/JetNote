package com.example.quick_note
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.local.model.Note
import com.example.note.NoteVM
import java.util.UUID
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Quick(
    noteVM: QuickNoteVM = hiltViewModel(),
    fin:() -> (Unit)
    ) {
    val descriptionState = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
    ) {
        TextField(value = descriptionState.value, onValueChange = {descriptionState.value = it})
        Button(onClick = {
            noteVM.addQuickNote(
                Note(
                    description = descriptionState.value,
                    uid = UUID.randomUUID().toString(),
                    color = 987654,
                    textColor = 854313,
                )
            )
            fin.invoke()
        }) {
            Text(text = "Done")
        }
    }
}