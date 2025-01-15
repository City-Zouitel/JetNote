package city.zouitel.tags.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.logic.events.UiEvent
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonIcons.FULL_LABEL_ICON
import city.zouitel.systemDesign.CommonIcons.OUTLINE_LABEL_ICON
import city.zouitel.systemDesign.CommonTextField
import city.zouitel.tags.model.Tag
import city.zouitel.tags.model.NoteAndTag as InNoteAndTag

data class TagsScreen(val uid: String): Screen {

    @Composable
    override fun Content() {

        Tags(
            tagModel = getScreenModel(),
            noteAndTagModel = getScreenModel(),
        )
    }

    @OptIn(
        ExperimentalComposeUiApi::class,
        ExperimentalLayoutApi::class
    )
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun Tags(
        tagModel: TagScreenModel,
        noteAndTagModel: NoteAndTagScreenModel,
    ) {
        val keyboardManager = LocalFocusManager.current
        val navigator = LocalNavigator.current

        val observeTags by remember(tagModel, tagModel::getAllLTags).collectAsState()
        val observeNoteAndTag by remember(
            noteAndTagModel,
            noteAndTagModel::getAllNotesAndTags
        ).collectAsState()
        val uiState by remember(tagModel, tagModel::uiState).collectAsState()
        var maxLines by remember { mutableIntStateOf(3) }
        val fieldState = rememberTextFieldState(uiState.currentLabel)

        val focusRequester by lazy { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.outlineVariant,
                    contentColor = contentColorFor(
                        backgroundColor = MaterialTheme.colorScheme.outlineVariant
                    ),
                    onClick = { navigator?.pop() }) {
                    Icon(
                        painter = painterResource(CommonIcons.DONE_ICON),
                        contentDescription = null
                    )
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 25.dp)
            ) {
                item {
                    uid.let { _id ->
                        ContextualFlowRow(
                            modifier = Modifier.animateContentSize(),
                            itemCount = observeTags.size,
                            maxLines = maxLines,
                            overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
                                expandIndicator = {
                                    ElevatedFilterChip(
                                        selected = true,
                                        onClick = { maxLines += 2 },
                                        label = {
                                            Text("+${totalItemCount - shownItemCount}")
                                        }
                                    )
                                },
                                collapseIndicator = {
                                    ElevatedFilterChip(
                                        selected = true,
                                        onClick = { maxLines = 3 },
                                        label = {
                                            Icon(
                                                modifier = Modifier.size(15.dp),
                                                painter = painterResource(CommonIcons.CROSS_ICON),
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                            )
                        ) { index ->
                            val noteAndTag = InNoteAndTag(noteUid = _id, labelId = observeTags[index].id)

                            ElevatedFilterChip(
                                modifier = Modifier.padding(2.dp),
                                selected = true,
                                onClick = {
                                    val uiEvent = when {
                                        observeNoteAndTag.contains(noteAndTag) -> UiEvent.Delete(noteAndTag)
                                        else -> UiEvent.Insert(noteAndTag)
                                    }
                                    noteAndTagModel.sendUiEvent(uiEvent)
                                },
                                leadingIcon = {
                                    if (observeNoteAndTag.contains(noteAndTag)) {
                                        Icon(
                                            painterResource(FULL_LABEL_ICON), null,
                                            tint = if (observeTags[indexInLine].color == Color.Transparent.toArgb()) {
                                                MaterialTheme.colorScheme.surfaceTint
                                            } else Color(observeTags[index].color)
                                        )
                                    } else {
                                        Icon(
                                            painterResource(OUTLINE_LABEL_ICON), null,
                                            tint = if (observeTags[index].color == Color.Transparent.toArgb()) {
                                                MaterialTheme.colorScheme.surfaceTint
                                            } else Color(observeTags[index].color)
                                        )
                                    }
                                },
                                label = { observeTags[index].label?.let { Text(it) } }
                            )
                        }
                    }
                }

                item {
                    CommonTextField(
                        state = fieldState,
                        receiver = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusEvent { keyboardManager.moveFocus(FocusDirection.Enter) },
                        placeholder = "Tag..",
                        imeAction = ImeAction.Done,
                        keyboardAction = {
                            if (observeTags.any { it.id == uiState.currentId }) {
                                tagModel.sendUiEvent(
                                    UiEvent.Update(
                                        data = Tag(
                                            id = uiState.currentId,
                                            label = uiState.currentLabel,
                                            color = uiState.currentColor
                                        )
                                    )
                                )
                            } else {
                                tagModel.sendUiEvent(
                                    UiEvent.Insert(
                                        data = Tag(
                                            label = fieldState.text.toString(),
                                            color = uiState.currentColor
                                        )
                                    )
                                )
                            }
                            tagModel.updateColor().updateId()
                            fieldState.clearText()
                        }
                    )
                }
            }
        }
    }
}