package city.zouitel.screens.note_card

import android.text.format.DateFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.model.Data
import city.zouitel.note.model.Note
import city.zouitel.systemDesign.CommonConstants.KEY_CLICK
import city.zouitel.systemDesign.CommonConstants.NON
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.CommonIcons.ANGLE_DOWN_ICON
import city.zouitel.systemDesign.CommonIcons.ANGLE_UP_ICON
import city.zouitel.systemDesign.CommonIcons.CIRCLE_ICON_18
import city.zouitel.systemDesign.CommonIcons.RESET_ICON
import city.zouitel.systemDesign.ImageDisplayed
import city.zouitel.tasks.ui.NoteAndTaskScreenModel
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.Task
import city.zouitel.screens.sound
import city.zouitel.note.ui.workplace.WorkplaceScreen
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.normalNotePath
import city.zouitel.screens.prioritizedNotePath
import city.zouitel.systemDesign.CommonConstants.LIST
import city.zouitel.systemDesign.CommonIcons.BELL_RING_ICON
import city.zouitel.tasks.ui.TaskScreenModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState
import java.util.*

@Composable
fun NoteCard(
    dataStoreModel: DataStoreScreenModel,
    taskModel: TaskScreenModel,
    noteAndTaskModel: NoteAndTaskScreenModel,
    dataModel: DataScreenModel,
    audioModel: AudioScreenModel,
    noteAndAudioModel: NoteAndAudioScreenModel,
    linkModel: LinkScreenModel,
    noteAndLinkModel: NoteAndLinkScreenModel,
    isHomeScreen: Boolean,
    noteEntity: Note,
    homeModel: MainScreenModel,
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
                dataModel = dataModel,
                audioModel = audioModel,
                noteAndAudioModel = noteAndAudioModel,
                dataStoreModel = dataStoreModel,
                linkModel = linkModel,
                noteAndLinkModel = noteAndLinkModel,
                noteEntity = noteEntity,
                isHomeScreen = isHomeScreen,
                mainModel = homeModel
            )
        }
    } else {
        Card(
            taskModel = taskModel,
            noteAndTodoModel = noteAndTaskModel,
            dataModel = dataModel,
            audioModel = audioModel,
            noteAndAudioModel = noteAndAudioModel,
            dataStoreModel = dataStoreModel,
            linkModel = linkModel,
            noteAndLinkModel = noteAndLinkModel,
            noteEntity = noteEntity,
            isHomeScreen = isHomeScreen,
            mainModel = homeModel
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Card(
    taskModel: TaskScreenModel,
    noteAndTodoModel: NoteAndTaskScreenModel,
    dataModel: DataScreenModel,
    dataStoreModel: DataStoreScreenModel,
    linkModel: LinkScreenModel,
    noteAndLinkModel: NoteAndLinkScreenModel,
    audioModel: AudioScreenModel,
    noteAndAudioModel: NoteAndAudioScreenModel,
    noteEntity: Note,
    isHomeScreen: Boolean,
    mainModel: MainScreenModel
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
                    /*show return note dialog.*/
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

        // display the image.
        when (isHomeScreen) {
            true, false -> {
                ImageDisplayed(media = dataModel::imageDecoder.invoke(context, note.uid))
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
        LazyRow {
            items(items = labels) { label ->
                AssistChip(
                    modifier = Modifier.alpha(.7f),
                    border = BorderStroke(0.dp, Color.Transparent),
                    onClick = { },
                    label = {
                        label.label?.let { Text(it, fontSize = 11.sp) }
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = CIRCLE_ICON_18),
                            contentDescription = null,
                            tint = Color(label.color),
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
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!isHomeScreen) {
                IconButton(onClick = {
                    dataModel.editData(
                        Data(
                            title = note.title,
                            description = note.description,
                            priority = note.priority,
                            uid = note.uid,
                            removed = 0,
                            color = note.color,
                            textColor = note.textColor
                        )
                    )
                }) {
                    Icon(
                        painterResource(id = RESET_ICON), null,
                        tint = Color(note.textColor)
                    )
                }
            }

            //display the reminding chip.
            if (isHomeScreen && note.reminding != 0L) {
                note.reminding.let {
                    runCatching {
                        ElevatedAssistChip(
                            modifier = Modifier.padding(5.dp),
                            onClick = {},
                            label = {
                                Text(
                                    DateFormat.format("yyyy-MM-dd HH:mm", Date(it)).toString(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textDecoration = if (it < Calendar.getInstance().time.time) {
                                        TextDecoration.LineThrough
                                    } else {
                                        TextDecoration.None
                                    },
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                )
                            },
                            leadingIcon = {
                                if (it >= Calendar.getInstance().time.time) {
                                    Icon(
                                        painterResource(BELL_RING_ICON),
                                        null,
                                        tint = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                }
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color(.6f, .6f, .6f, .5f)
                            ),
                            elevation = AssistChipDefaults.assistChipElevation()
                        )
                    }
                }
            }
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
//        Spacer(modifier = Modifier.height(15.dp))
    }
}

