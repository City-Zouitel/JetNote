package city.zouitel.tags.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.systemDesign.Icons.CIRCLE_ICON_18
import city.zouitel.systemDesign.Icons.FULL_LABEL_ICON
import city.zouitel.systemDesign.Icons.OUTLINE_LABEL_ICON
import city.zouitel.tags.state.State
import city.zouitel.tags.utils.DialogColors
import city.zouitel.tags.utils.HashTagLayout
import city.zouitel.tags.viewmodel.NoteAndTagScreenModel
import city.zouitel.tags.viewmodel.TagScreenModel
import com.google.accompanist.flowlayout.FlowRow
import org.koin.core.component.KoinComponent
import city.zouitel.tags.model.NoteAndTag as InNoteAndTag
import city.zouitel.tags.model.Tag as InTag

data class TagsScreen(val id: String? = null): Screen, KoinComponent {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val tagModel = getScreenModel<TagScreenModel>()
        val noteAndTagModel = getScreenModel<NoteAndTagScreenModel>()

        val tagState = State.Tag(tagModel)
        val noteAndTagState = State.NoteTag(noteAndTagModel)

        val allTags = tagState.rememberAllTags
        val allNoteAndTags = noteAndTagState.rememberAllNoteTags
        val idState = remember { mutableLongStateOf(-1L) }
        val labelState = remember { mutableStateOf("") }
        val colorState = remember { mutableIntStateOf(Color.Transparent.toArgb()) }
        val labelDialogState = remember { mutableStateOf(false) }

        if (labelDialogState.value) {
            DialogColors(
                tagModel = tagModel,
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
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 25.dp)
            ) {
                item {
                    id?.let {
                        FlowRow(mainAxisSpacing = 3.dp) {
                            allTags.forEach { label ->
                                ElevatedFilterChip(
                                    selected = true,
                                    modifier = Modifier,
                                    onClick = {
                                        if (allNoteAndTags.contains(InNoteAndTag(id, label.id))) {
                                            noteAndTagModel.deleteNoteAndTag(
                                                InNoteAndTag(
                                                    noteUid = id,
                                                    labelId = label.id
                                                )
                                            )
                                        } else {
                                            noteAndTagModel.addNoteAndTag(
                                                InNoteAndTag(
                                                    noteUid = id,
                                                    labelId = label.id
                                                )
                                            )
                                        }
                                    },
                                    leadingIcon = {
                                        if (allNoteAndTags.contains(InNoteAndTag(id, label.id))
                                        ) {
                                            Icon(
                                                painterResource(FULL_LABEL_ICON), null,
                                                tint = if (label.color == Color.Transparent.toArgb()) {
                                                    MaterialTheme.colorScheme.surfaceTint
                                                } else Color(label.color)
                                            )
                                        } else {
                                            Icon(
                                                painterResource(OUTLINE_LABEL_ICON), null,
                                                tint = if (label.color == Color.Transparent.toArgb()) {
                                                    MaterialTheme.colorScheme.surfaceTint
                                                } else Color(label.color)
                                            )
                                        }
                                    },
                                    label = { label.label?.let { Text(it) } }
                                )
                            }
                        }
                    } ?: HashTagLayout(
                        tagModel = tagModel,
                        labelDialogState = labelDialogState,
                        hashTags = allTags,
                        idState = idState,
                        labelState = labelState
                    )
                }

                item {
                    OutlinedTextField(
                        value = labelState.value,
                        onValueChange = { labelState.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        placeholder = {
                            Text("Tag..", color = Color.Gray, fontSize = 19.sp)
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = CIRCLE_ICON_18),
                                contentDescription = null,
                                tint = Color(colorState.intValue)
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
                                if (allTags.any { it.id == idState.longValue }) {
                                    tagModel.updateTag(
                                        InTag(
                                            id = idState.longValue,
                                            label = labelState.value,
                                            color = colorState.intValue
                                        )
                                    )
                                } else {
                                    tagModel.addTag(
                                        InTag(
                                            label = labelState.value,
                                            color = colorState.intValue
                                        )
                                    )
                                }.invokeOnCompletion {
                                    labelState.value = ""
                                    idState.longValue = -1
                                    colorState.intValue = 0x0000
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
}