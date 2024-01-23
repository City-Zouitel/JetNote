package city.zouitel.tags.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.systemDesign.Cons.NUL
import city.zouitel.systemDesign.Icons.CIRCLE_ICON_18
import city.zouitel.systemDesign.Icons.FULL_LABEL_ICON
import city.zouitel.systemDesign.Icons.OUTLINE_LABEL_ICON
import city.zouitel.systemDesign.MaterialColors
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE_TINT
import city.zouitel.tags.state.State
import city.zouitel.tags.viewmodel.NoteAndTagViewModel
import city.zouitel.tags.viewmodel.TagViewModel
import com.google.accompanist.flowlayout.FlowRow
import org.koin.androidx.compose.koinViewModel
import city.zouitel.tags.model.NoteAndTag as InNoteAndTag
import city.zouitel.tags.model.Tag as InTag

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "StateFlowValueCalledInComposition",
    "UnrememberedMutableState",
    "UnusedMaterialScaffoldPaddingParameter"
)
@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun Tags(
    tagViewModel: TagViewModel = koinViewModel(),
    noteAndTagViewModel: NoteAndTagViewModel = koinViewModel(),
    noteUid: String?,
) {
    val tagState = State.Tag(tagViewModel)
    val noteAndTagState = State.NoteTag(noteAndTagViewModel)

    val allTags = tagState.rememberAllTags
    val allNoteAndTags = noteAndTagState.rememberAllNoteTags

    val idState = remember { mutableStateOf(-1L) }
    val labelState = remember { mutableStateOf("") }//.filterBadWords()
    val colorState = remember { mutableStateOf(Color.Transparent.toArgb()) }

    val labelDialogState = remember { mutableStateOf(false) }

    val getMatColor = MaterialColors().getMaterialColor
    if (labelDialogState.value) {
            DialogColors(
                dialogState = labelDialogState,
                idState = idState,
                labelState = labelState,
                colorState = colorState
            )
    }
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(getMatColor(SURFACE))
                .padding(top = 25.dp)
        ) {

            item {
                if (noteUid == NUL) {
                    HashTagLayout(
                        labelDialogState = labelDialogState,
                        hashTags = allTags,
                        idState = idState,
                        labelState = labelState
                    )
                } else {
                    FlowRow(
                        mainAxisSpacing = 3.dp
                    ) {
                        allTags.forEach { label ->
                            ElevatedFilterChip(
                                selected = true,
                                modifier = Modifier,
                                onClick = {
                                    if (allNoteAndTags.contains(
                                            InNoteAndTag(noteUid!!, label.id)
                                        )
                                    ) {
                                        noteAndTagViewModel.deleteNoteAndTag(
                                            InNoteAndTag(
                                                noteUid = noteUid,
                                                labelId = label.id
                                            )
                                        )
                                    } else {
                                        noteAndTagViewModel.addNoteAndTag(
                                            InNoteAndTag(
                                                noteUid = noteUid,
                                                labelId = label.id
                                            )
                                        )
                                    }
                                },
                                leadingIcon = {
                                    if (
                                        allNoteAndTags.contains(
                                            InNoteAndTag(noteUid!!, label.id)
                                        )
                                    ) {
                                        Icon(
                                            painterResource(FULL_LABEL_ICON), null,
                                            tint = if (label.color == Color.Transparent.toArgb()) {
                                                getMatColor(SURFACE_TINT)
                                            } else Color(label.color)
                                        )
                                    } else {
                                        Icon(
                                            painterResource(OUTLINE_LABEL_ICON), null,
                                            tint = if (label.color == Color.Transparent.toArgb()) {
                                                getMatColor(SURFACE_TINT)
                                            } else Color(label.color)
                                        )
                                    }
                                },
                                label = {
                                    label.label?.let { Text(it) }
                                }
                            )
                        }
                    }
                }
            }
            item {
                OutlinedTextField(
                    value = labelState.value,
                    onValueChange = { labelState.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(getMatColor(SURFACE)),
                    placeholder = {
                        Text("Tag..", color = Color.Gray, fontSize = 19.sp)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = CIRCLE_ICON_18),
                            contentDescription = null,
                            tint = Color(colorState.value)
                        )
                    },
                    maxLines = 1,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Default,
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (allTags.any { it.id == idState.value }) {
                                tagViewModel.updateTag(
                                    InTag(
                                        id = idState.value,
                                        label = labelState.value,
                                        color = colorState.value
                                    )
                                )
                            } else {
                                tagViewModel.addTag(
                                    InTag(
                                        label = labelState.value,
                                        color = colorState.value
                                    )
                                )
                            }.invokeOnCompletion {
                                labelState.value = ""
                                idState.value = -1
                                colorState.value = 0x0000
                            }
                        }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }
        }
    }
}



