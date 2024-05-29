package city.zouitel.tags.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.systemDesign.CommonTextField
import city.zouitel.systemDesign.Icons.FULL_LABEL_ICON
import city.zouitel.systemDesign.Icons.OUTLINE_LABEL_ICON
import city.zouitel.tags.utils.DialogColors
import city.zouitel.tags.utils.HashTagLayout
import com.google.accompanist.flowlayout.FlowRow
import org.koin.core.component.KoinComponent
import city.zouitel.tags.model.NoteAndTag as InNoteAndTag
import city.zouitel.tags.model.Tag as InTag

data class TagsScreen(val id: String? = null): Screen, KoinComponent {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
        ExperimentalComposeUiApi::class
    )
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val keyboardManager = LocalFocusManager.current

        val tagModel = getScreenModel<TagScreenModel>()
        val noteAndTagModel = getScreenModel<NoteAndTagScreenModel>()

        val observeTags by remember(tagModel, tagModel::getAllLTags).collectAsState()
        val observeNoteAndTag by remember(noteAndTagModel, noteAndTagModel::getAllNotesAndTags).collectAsState()
        val textFieldState = rememberTextFieldState("")

        val uiState by lazy { tagModel.uiState }
        val focusRequester by lazy { FocusRequester() }

        if (uiState.colorsDialogState) {
            DialogColors(tagModel = tagModel, textFieldState = textFieldState)
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }


        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
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
                            observeTags.forEach { label ->
                                ElevatedFilterChip(
                                    selected = true,
                                    modifier = Modifier,
                                    onClick = {
                                        if (observeNoteAndTag.contains(InNoteAndTag(id, label.id))) {
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
                                        if (observeNoteAndTag.contains(InNoteAndTag(id, label.id))
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
                        hashTags = observeTags
                    )
                }

                item {
                    CommonTextField(
                        state = textFieldState,
                        placeholder = "Tag..",
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .onFocusEvent { keyboardManager.moveFocus(FocusDirection.Enter) },
                        imeAction = ImeAction.Done,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (observeTags.any { it.id == uiState.currentId }) {
                                    tagModel.updateTag(
                                        InTag(
                                            id = uiState.currentId,
                                            label = textFieldState.text.toString(),
                                            color = uiState.currentColor
                                        )
                                    )
                                } else {
                                    tagModel.addTag(
                                        InTag(
                                            label = textFieldState.text.toString(),
                                            color = uiState.currentColor
                                        )
                                    )
                                }.invokeOnCompletion {
                                    textFieldState.clearText()
                                    tagModel.updateColor().updateId()
                                }
                            }
                        )
                    )

//                    OutlinedTextField(
//                        value = uiState.currentLabel,
//                        onValueChange = { tagModel.updateLabel(it) },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(MaterialTheme.colorScheme.surface),
//                        placeholder = {
//                            Text("Tag..", color = Color.Gray, fontSize = 19.sp)
//                        },
//                        leadingIcon = {
//                            Icon(
//                                painter = painterResource(id = CIRCLE_ICON_18),
//                                contentDescription = null,
//                                tint = Color(uiState.currentColor)
//                            )
//                        },
//                        maxLines = 1,
//                        textStyle = TextStyle(
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Normal,
//                            fontFamily = FontFamily.Default,
//                        ),
//                        keyboardOptions = KeyboardOptions(
//                            capitalization = KeyboardCapitalization.Sentences,
//                            autoCorrect = false,
//                            keyboardType = KeyboardType.Text,
//                            imeAction = ImeAction.Done
//                        ),
//                        keyboardActions = KeyboardActions(
//                            onDone = {
//                                if (observeTags.any { it.id == uiState.currentId }) {
//                                    tagModel.updateTag(
//                                        InTag(
//                                            id = uiState.currentId,
//                                            label = uiState.currentLabel,
//                                            color = uiState.currentColor
//                                        )
//                                    )
//                                } else {
//                                    tagModel.addTag(
//                                        InTag(
//                                            label = uiState.currentLabel,
//                                            color = uiState.currentColor
//                                        )
//                                    )
//                                }.invokeOnCompletion {
//                                    tagModel.updateLabel()
//                                        .updateColor()
//                                        .updateId()
//                                }
//                            }
//                        ),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            focusedBorderColor = Color.Transparent,
//                            unfocusedBorderColor = Color.Transparent
//                        )
//                    )
                }
            }
        }
    }
}