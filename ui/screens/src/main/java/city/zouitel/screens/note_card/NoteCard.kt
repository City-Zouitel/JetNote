package city.zouitel.screens.note_card

import android.net.Uri
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
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import androidx.core.net.toUri
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.audios.ui.MiniMediaPlayer
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.LinkPart
import city.zouitel.links.ui.LinkVM
import city.zouitel.links.ui.NoteAndLinkVM
import city.zouitel.logic.codeUrl
import city.zouitel.screens.navigation_drawer.Screens
import city.zouitel.screens.navigation_drawer.Screens.*
import city.zouitel.note.DataScreenModel
import city.zouitel.note.model.Data
import city.zouitel.note.model.Note
import city.zouitel.systemDesign.Cons.REC_DIR
import city.zouitel.systemDesign.Cons.IMG_DIR
import city.zouitel.systemDesign.Cons.JPEG
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.Cons.MP3
import city.zouitel.systemDesign.Cons.NON
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.ANGLE_DOWN_ICON
import city.zouitel.systemDesign.Icons.ANGLE_UP_ICON
import city.zouitel.systemDesign.Icons.CIRCLE_ICON_18
import city.zouitel.systemDesign.Icons.RESET_ICON
import city.zouitel.systemDesign.ImageDisplayed
import city.zouitel.tasks.viewmodel.NoteAndTaskScreenModel
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.Task
import city.zouitel.screens.sound
import city.zouitel.note.ui.edit_screen.EditScreen
import city.zouitel.screens.normalNotePath
import city.zouitel.screens.prioritizedNotePath
import city.zouitel.systemDesign.Cons.LIST
import city.zouitel.systemDesign.Icons.BELL_RING_ICON
import city.zouitel.tasks.viewmodel.TaskScreenModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.util.*

@Composable
fun NoteCard(
    dataStoreVM: DataStoreVM = koinViewModel(),
    taskModel: TaskScreenModel,
    noteAndTaskModel: NoteAndTaskScreenModel,
    dataModel: DataScreenModel,
    screen: Screens,
    noteEntity: Note,
    homeSelectionState: MutableState<Boolean>?,
    trashSelectionState: MutableState<Boolean>?,
    selectedNotes: SnapshotStateList<Data>?,
    onSwipeNote: (Note) -> Unit
) {
    val swipeState = rememberSwipeableActionsState()
    val currentLayout = remember(dataStoreVM, dataStoreVM::getLayout).collectAsState()

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
                noteEntity = noteEntity,
                screen = screen,
                homeSelectionState = homeSelectionState,
                trashSelectionState = trashSelectionState,
                selectedNotes = selectedNotes
            )
        }
    } else {
        Card(
            taskModel = taskModel,
            noteAndTodoModel = noteAndTaskModel,
            dataModel = dataModel,
            noteEntity = noteEntity,
            screen = screen,
            homeSelectionState = homeSelectionState,
            trashSelectionState = trashSelectionState,
            selectedNotes = selectedNotes
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Card(
    taskModel: TaskScreenModel,
    noteAndTodoModel: NoteAndTaskScreenModel,
    dataModel: DataScreenModel,
    dataStoreVM: DataStoreVM = koinViewModel(),
    linkVM: LinkVM = koinViewModel(),
    noteAndLinkVM: NoteAndLinkVM = koinViewModel(),
    noteEntity: Note,
    screen: Screens,
    homeSelectionState: MutableState<Boolean>?,
    trashSelectionState: MutableState<Boolean>?,
    selectedNotes: SnapshotStateList<Data>?
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val note = noteEntity.dataEntity
    val labels = noteEntity.tagEntities
    val internalPath = context.filesDir.path

    val observeTodoList = remember(taskModel, taskModel::getAllTaskList).collectAsState()
    val observeNoteAndTodo =
        remember(noteAndTodoModel, noteAndTodoModel::getAllNotesAndTask).collectAsState()

    val observerLinks = remember(linkVM, linkVM::getAllLinks).collectAsState()
    val observerNoteAndLink =
        remember(noteAndLinkVM, noteAndLinkVM::getAllNotesAndLinks).collectAsState()

    val mediaPath = context.filesDir.path + "/$REC_DIR/" + note.uid + "." + MP3
    val imagePath = "$internalPath/$IMG_DIR/${note.uid}.$JPEG"

    var todoListState by remember { mutableStateOf(false) }
    val media = remember { mutableStateOf<Uri?>(File(imagePath).toUri()) }

    val haptic = LocalHapticFeedback.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .combinedClickable(
                onLongClick = {
                    // To make vibration.
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                    when (screen) {
                        HOME_SCREEN -> {
                            homeSelectionState?.value = true
                        }

                        DELETED_SCREEN -> {
                            trashSelectionState?.value = true
                        }

                        else -> {}
                    }
                    selectedNotes?.add(note)
                }
            ) {
                sound.makeSound.invoke(context, KEY_CLICK, thereIsSoundEffect.value)

                if (screen == HOME_SCREEN && !homeSelectionState?.value!!) {
                    navigator?.push(
                        EditScreen(
                            note.uid,
                            codeUrl.invoke(note.title),
                            codeUrl.invoke(note.description),
                            note.color,
                            note.textColor,
                            note.priority,
                            note.audioDuration,
                            note.reminding
                        )
                    )
                } else if (screen == DELETED_SCREEN && !trashSelectionState?.value!!) {
                    /*do nothing.*/
                } else {
                    when {
                        !selectedNotes?.contains(note)!! -> selectedNotes.add(note)
                        else -> selectedNotes.remove(note)
                    }
                }
                selectedNotes?.ifEmpty {
                    homeSelectionState?.value = false
                    trashSelectionState?.value = false
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
            if(selectedNotes?.contains(note) == true) {
                when(screen) {
                    HOME_SCREEN -> BorderStroke(3.dp, Color.Cyan)
                    DELETED_SCREEN -> BorderStroke(3.dp, Color.Red)
                    else -> { throw Exception("") }
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
        when (screen) {
            HOME_SCREEN, DELETED_SCREEN -> {
                ImageDisplayed(media = dataModel::imageDecoder.invoke(context, note.uid))
            }
            else -> { // Timber.tag(TAG).d("")
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
        if (
            File(mediaPath).exists()
        ) {
            MiniMediaPlayer(localMediaUid = note.uid)
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
            if (screen == DELETED_SCREEN) {
                IconButton(onClick = {
                    dataModel.editData(
                        Data(
                            title = note.title,
                            description = note.description,
                            priority = note.priority,
                            uid = note.uid,
                            trashed = 0,
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
            if (screen == HOME_SCREEN && note.reminding != 0L) {
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
            LinkPart(
                linkVM = linkVM,
                noteAndLinkVM = noteAndLinkVM,
                noteUid = note.uid,
                swipeable = false,
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
        Spacer(modifier = Modifier.height(15.dp))
    }
}

