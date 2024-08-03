package city.zouitel.screens.note_card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastLastOrNull
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.audios.model.NoteAndAudio
import city.zouitel.audios.ui.component.AudioScreenModel
import city.zouitel.audios.ui.component.MiniAudioPlayer
import city.zouitel.audios.ui.component.NoteAndAudioScreenModel
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.LinkCard
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.links.ui.NoteAndLinkScreenModel
import city.zouitel.media.model.NoteAndMedia
import city.zouitel.media.ui.MediaScreenModel
import city.zouitel.media.ui.NoteAndMediaScreenModel
import city.zouitel.note.model.Note
import city.zouitel.note.ui.workplace.WorkplaceScreen
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.normalNotePath
import city.zouitel.screens.prioritizedNotePath
import city.zouitel.screens.sound
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonConstants.LIST
import city.zouitel.systemDesign.CommonConstants.NON
import city.zouitel.systemDesign.CommonIcons.ANGLE_DOWN_ICON
import city.zouitel.systemDesign.CommonIcons.ANGLE_UP_ICON
import city.zouitel.systemDesign.CommonIcons.CIRCLE_ICON_18
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.Task
import city.zouitel.tasks.ui.NoteAndTaskScreenModel
import city.zouitel.tasks.ui.TaskScreenModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState

@Composable
fun NoteCard(
    dataStoreModel: DataStoreScreenModel,
    taskModel: TaskScreenModel,
    noteAndTaskModel: NoteAndTaskScreenModel,
    audioModel: AudioScreenModel,
    noteAndAudioModel: NoteAndAudioScreenModel,
    linkModel: LinkScreenModel,
    noteAndLinkModel: NoteAndLinkScreenModel,
    isHomeScreen: Boolean,
    noteEntity: Note,
    homeModel: MainScreenModel,
    mediaModel: MediaScreenModel,
    noteAndMediaModel: NoteAndMediaScreenModel,
    onSwipeNote: (Note) -> Unit,
) {
    val swipeState = rememberSwipeableActionsState()
    val currentLayout = remember(dataStoreModel, dataStoreModel::getLayout).collectAsState()

    val action = SwipeAction(
        onSwipe = {
            onSwipeNote.invoke(noteEntity)
        },
        icon = {},
        background = Color.Red
    )

    if (currentLayout.value == LIST) {
        SwipeableActionsBox(
            modifier = Modifier,
            backgroundUntilSwipeThreshold = Color.Transparent,
            endActions = listOf(action),
            swipeThreshold = 100.dp,
            state = swipeState
        ) {
            Card(
                taskModel = taskModel,
                noteAndTodoModel = noteAndTaskModel,
                audioModel = audioModel,
                noteAndAudioModel = noteAndAudioModel,
                dataStoreModel = dataStoreModel,
                linkModel = linkModel,
                noteAndLinkModel = noteAndLinkModel,
                noteEntity = noteEntity,
                isHomeScreen = isHomeScreen,
                mainModel = homeModel,
                mediaModel = mediaModel,
                noteAndMediaModel = noteAndMediaModel
            )
        }
    } else {
        Card(
            taskModel = taskModel,
            noteAndTodoModel = noteAndTaskModel,
            audioModel = audioModel,
            noteAndAudioModel = noteAndAudioModel,
            dataStoreModel = dataStoreModel,
            linkModel = linkModel,
            noteAndLinkModel = noteAndLinkModel,
            noteEntity = noteEntity,
            isHomeScreen = isHomeScreen,
            mainModel = homeModel,
            mediaModel = mediaModel,
            noteAndMediaModel = noteAndMediaModel
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
private fun Card(
    taskModel: TaskScreenModel,
    noteAndTodoModel: NoteAndTaskScreenModel,
    dataStoreModel: DataStoreScreenModel,
    linkModel: LinkScreenModel,
    noteAndLinkModel: NoteAndLinkScreenModel,
    audioModel: AudioScreenModel,
    noteAndAudioModel: NoteAndAudioScreenModel,
    noteEntity: Note,
    isHomeScreen: Boolean,
    mainModel: MainScreenModel,
    mediaModel: MediaScreenModel,
    noteAndMediaModel: NoteAndMediaScreenModel
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val haptic = LocalHapticFeedback.current

    val note = noteEntity.dataEntity
    val labels = noteEntity.tagEntities

    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()
    val observeTodoList = remember(taskModel, taskModel::getAllTaskList).collectAsState()
    val observeNoteAndTodo =
        remember(noteAndTodoModel, noteAndTodoModel::getAllNotesAndTask).collectAsState()

    val observerLinks = remember(linkModel, linkModel::getAllLinks).collectAsState()
    val observerNoteAndLink =
        remember(noteAndLinkModel, noteAndLinkModel::getAllNotesAndLinks).collectAsState()
    val observerAudios by remember(audioModel, audioModel::allAudios).collectAsState()
    val observerNoteAndAudio by remember(noteAndAudioModel, noteAndAudioModel::allNoteAndAudio)
        .collectAsState()
    val uiState by remember(mainModel, mainModel::uiState).collectAsState()

    var todoListState by remember { mutableStateOf(false) }

    val observeMedias by remember(mediaModel, mediaModel::allMedias).collectAsState()
    val observeNotesAndMedia by remember(
        noteAndMediaModel,
        noteAndMediaModel::getAllNotesAndMedia
    ).collectAsState()

    val filteredMedias = observeMedias.filter {
        observeNotesAndMedia.contains(
            NoteAndMedia(note.uid, it.id)
        )
    }

    val pagerState = rememberPagerState { filteredMedias.size }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .combinedClickable(
                onLongClick = {
                    // To make vibration.
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    mainModel.updateSelectedNotes(note)
                    mainModel.updateSelection(true)
                }
            ) {
                sound.makeSound.invoke(context, KEY_CLICK, thereIsSoundEffect.value)

                if (isHomeScreen && !uiState.isSelection) {
                    navigator?.push(
                        WorkplaceScreen(
                            id = note.uid,
                            isNew = false,
                            title = note.title,
                            description = note.description,
                            backgroundColor = note.color,
                            textColor = note.textColor,
                            priority = note.priority,
                            reminding = note.reminding
                        )
                    )
                } else if (!isHomeScreen && !uiState.isSelection) {
                    mainModel.updateOptionsDialog(true)
                    mainModel.updateSelectedNote(note)
                } else {
                    when {
                        !uiState.selectedNotes.contains(note) -> uiState.selectedNotes.add(note)
                        else -> uiState.selectedNotes.remove(note)
                    }
                }
                uiState.selectedNotes.ifEmpty {
                    mainModel.updateSelection(false)
                }
            }
            .drawBehind {
                if (note.priority.equals(NON, true)) {
                    normalNotePath(note)
                } else {
                    prioritizedNotePath(note)
                }
            },
        shape = AbsoluteRoundedCornerShape(15.dp),
        border =
            if(uiState.selectedNotes.contains(note)) {
                when(isHomeScreen) {
                    true -> BorderStroke(3.dp, Color.Cyan)
                    false -> BorderStroke(3.dp, Color.Red)
                }
            } else {
                BorderStroke(0.dp, Color.Transparent)
            } ,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)

    ) {

        if (filteredMedias.isNotEmpty()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.background(Color(note.color))
            ) { index ->
                BadgedBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(note.color)),
                    badge = {
                        if (filteredMedias.count() > 1) {
                            Badge(
                                modifier = Modifier.padding(3.dp),
                                contentColor = Color.White.copy(alpha = .5f),
                                containerColor = Color.Black.copy(alpha = .5f)
                            ) {
                                Text(text = "${index + 1}/${filteredMedias.count()}")
                            }
                        }
                    }
                ) {
                    Card(
                        shape = AbsoluteRoundedCornerShape(15.dp),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        runCatching {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(filteredMedias[index].path)
                                    .build(),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = note.title ?: "",
            fontSize = 19.sp,
            color = Color(note.textColor),
            modifier = Modifier.padding(3.dp)
        )

        Text(
            text = note.description ?: "",
            fontSize = 15.sp,
            color = Color(note.textColor),
            modifier = Modifier.padding(start = 3.dp, end = 3.dp, bottom = 7.dp)
        )

        //display media player.
        observerAudios.filter {
            observerNoteAndAudio.contains(
                NoteAndAudio(note.uid, it.id)
            )
        }.fastLastOrNull { _audio ->
            MiniAudioPlayer(dataStoreModel, audioModel, _audio)
            true
        }

        //display tags.
        ContextualFlowRow(
            itemCount = labels.size,
            maxLines = 1,
            overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
                expandIndicator = {
                    AssistChip(
                        modifier = Modifier.alpha(.5f),
                        border = BorderStroke(0.dp, Color.Transparent),
                        onClick = { },
                        label = {
                            Text("+${(totalItemCount - (shownItemCount - 1))}", fontSize = 11.sp)
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = Color(note.textColor)
                        ),
                        shape = CircleShape
                    )
                },
                collapseIndicator = { }
            )
        ) { index ->
                AssistChip(
                    modifier = Modifier.alpha(.7f),
                    border = BorderStroke(0.dp, Color.Transparent),
                    onClick = { },
                    label = {
                        labels[index].label?.let { Text(it, fontSize = 11.sp) }
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = CIRCLE_ICON_18),
                            contentDescription = null,
                            tint = Color(labels[index].color),
                            modifier = Modifier.size(10.dp)
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = Color(note.textColor)
                    ),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.width(3.dp))
        }

        // display link card.
        observerLinks.value.filter {
            observerNoteAndLink.value.contains(
                NoteAndLink(note.uid, it.id)
            )
        }.forEach { _link ->
            LinkCard(
                linkScreenModel = linkModel,
                noteAndLinkScreenModel = noteAndLinkModel,
                noteUid = note.uid,
                isSwipe = false,
                link = _link,
            )
        }

        // display tasks list.
        if (
            observeTodoList.value.any {
                observeNoteAndTodo.value.contains(
                    NoteAndTask(note.uid, it.id)
                )
            }
        ) {
            Icon(
                painterResource(
                    if (todoListState) ANGLE_UP_ICON else ANGLE_DOWN_ICON
                ),
                null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        todoListState = !todoListState
                    },
                tint = Color(note.textColor)
            )
        }

        AnimatedVisibility(visible = todoListState, modifier = Modifier.height(100.dp)) {
            LazyColumn {
                item {
                    observeTodoList.value.filter {
                        observeNoteAndTodo.value.contains(
                            NoteAndTask(note.uid, it.id)
                        )
                    }.forEach { todo ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = todo.isDone, onClick = {
                                taskModel.updateTotoItem(
                                    Task(
                                        id = todo.id,
                                        item = todo.item,
                                        isDone = !todo.isDone
                                    )
                                )
                            },
                                colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Gray,
                                unselectedColor = Color(note.textColor)
                            ),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(14.dp)
                            )
                            todo.item?.let { item ->
                                Text(
                                    text = item,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 14.sp,
                                    style = TextStyle(
                                        textDecoration = if (todo.isDone) {
                                            TextDecoration.LineThrough
                                        } else {
                                            TextDecoration.None
                                        },
                                        color = if (todo.isDone) Color.Gray else Color(note.textColor)
                                    ),
                                    modifier = Modifier.padding(3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}