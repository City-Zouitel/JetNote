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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.audio.ui.component.AudioScreenModel
import city.zouitel.audio.ui.component.SmallAudioPlayer
import city.zouitel.domain.utils.Action
import city.zouitel.links.ui.LinkCard
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.media.ui.MediaScreenModel
import city.zouitel.note.model.Note
import city.zouitel.note.ui.workplace.WorkplaceScreen
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.main_screen.OptionsScreen
import city.zouitel.screens.utils.sound
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonConstants.LIST
import city.zouitel.systemDesign.CommonConstants.NON
import city.zouitel.systemDesign.CommonIcons.ANGLE_DOWN_ICON
import city.zouitel.systemDesign.CommonIcons.ANGLE_UP_ICON
import city.zouitel.systemDesign.CommonIcons.CIRCLE_ICON_18
import city.zouitel.systemDesign.CommonSwipeItem
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tasks.ui.TaskScreenModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch

@Composable
fun NoteCard(
    dataStoreModel: DataStoreScreenModel,
    taskModel: TaskScreenModel,
    audioModel: AudioScreenModel,
    linkModel: LinkScreenModel,
    isHomeScreen: Boolean,
    noteEntity: Note,
    homeModel: MainScreenModel,
    mediaModel: MediaScreenModel,
    onSwipeNote: (Note) -> Unit,
) {
    val currentLayout by remember(dataStoreModel, dataStoreModel::getLayout).collectAsState()

    if (currentLayout == LIST) {
        CommonSwipeItem(
            enableRightDirection = false,
            onSwipeLeft = {
                onSwipeNote.invoke(noteEntity)
            }
        ) {
            Card(
                taskModel = taskModel,
                audioModel = audioModel,
                dataStoreModel = dataStoreModel,
                linkModel = linkModel,
                noteEntity = noteEntity,
                isHomeScreen = isHomeScreen,
                mainModel = homeModel,
                mediaModel = mediaModel,
            )
        }
    } else {
        Card(
            taskModel = taskModel,
            audioModel = audioModel,
            dataStoreModel = dataStoreModel,
            linkModel = linkModel,
            noteEntity = noteEntity,
            isHomeScreen = isHomeScreen,
            mainModel = homeModel,
            mediaModel = mediaModel,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
private fun Card(
    taskModel: TaskScreenModel,
    dataStoreModel: DataStoreScreenModel,
    linkModel: LinkScreenModel,
    audioModel: AudioScreenModel,
    noteEntity: Note,
    isHomeScreen: Boolean,
    mainModel: MainScreenModel,
    mediaModel: MediaScreenModel,
) {
    val note = noteEntity.dataEntity
    val labels = noteEntity.tagEntities

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val navigator = LocalNavigator.current
    val navBottomSheet = LocalBottomSheetNavigator.current

    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
    val observeAllTasks by remember(taskModel, taskModel::observeAllTasks).collectAsState()
    val observerAudios by remember(audioModel, audioModel::allAudios).collectAsState()
    val uiState by remember(mainModel, mainModel::uiState).collectAsState()

    var todoListState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val observerLinks by remember(linkModel, linkModel::observeAll).collectAsState()
    val filteredLinks = observerLinks.filter { it.uid == note.uid }

    val observeMedias by remember(mediaModel, mediaModel::observeAll).collectAsStateWithLifecycle()
    val filteredMedia = observeMedias.filter { it.uid == note.uid }
    val mediaCount = rememberPagerState { filteredMedia.size }

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
                sound.performSoundEffect(
                    context,
                    CommonConstants.KEY_CLICK,
                    thereIsSoundEffect.value
                )
                if (isHomeScreen && !uiState.isSelection) {
                    navigator?.push(
                        WorkplaceScreen(
                            uid = note.uid,
                            isNew = false,
                            title = note.title,
                            description = note.description,
                            backgroundColor = note.color,
                            textColor = note.textColor,
                            priority = note.priority
                        )
                    )
                } else if (!isHomeScreen && !uiState.isSelection) {
                    scope.launch {
                        mainModel.updateSelectedNote(note)
                        navBottomSheet.show(OptionsScreen())
                    }
                } else {
                    when {
                        !uiState.selectedNotes.contains(note) -> uiState.selectedNotes.add(note)
                        else -> uiState.selectedNotes.remove(note)
                    }
                }
                uiState.selectedNotes.ifEmpty {
                    mainModel.updateSelection(false)
                }
            }.drawBehind {
                if (note.priority.equals(NON, true)) {
                    normalNotePath(note)
                } else {
                    prioritizedNotePath(note)
                }
            },
        shape = AbsoluteRoundedCornerShape(15.dp),
        border =
        if (uiState.selectedNotes.contains(note)) {
            when (isHomeScreen) {
                true -> BorderStroke(3.dp, Color.Cyan)
                false -> BorderStroke(3.dp, Color.Red)
            }
        } else {
            BorderStroke(0.dp, Color.Transparent)
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)

    ) {
        // Media display.
        if (filteredMedia.isNotEmpty()) {
            HorizontalPager(
                state = mediaCount,
                modifier = Modifier.background(Color(note.color))
            ) { index ->
                BadgedBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(note.color)),
                    badge = {
                        if (filteredMedia.count() > 1) {
                            Badge(
                                modifier = Modifier.padding(3.dp),
                                contentColor = Color.White.copy(alpha = .5f),
                                containerColor = Color.Black.copy(alpha = .5f)
                            ) {
                                Text(text = "${index + 1}/${filteredMedia.count()}")
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
                                    .data(filteredMedia[index].uri)
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
        observerAudios.filter { it?.uid == note.uid }.fastLastOrNull {
            it?.let { audio ->
                SmallAudioPlayer(dataStoreModel, audioModel, audio)
            }
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
                    Text(labels.getOrNull(index)?.label ?: "", fontSize = 11.sp)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = CIRCLE_ICON_18),
                        contentDescription = null,
                        tint = Color(labels.getOrNull(index)?.color ?: 0),
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
        filteredLinks.map {
            LinkCard(isSwipe = false, link = it)
        }

        // display tasks list.
        if (observeAllTasks.any { it.uid == note.uid }) {
            Icon(
                painterResource(if (todoListState) ANGLE_UP_ICON else ANGLE_DOWN_ICON),
                null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { todoListState = !todoListState },
                tint = Color(note.textColor)
            )
        }

        AnimatedVisibility(visible = todoListState, modifier = Modifier.height(100.dp)) {
            LazyColumn {
                item {
                    observeAllTasks.filter { it.uid == note.uid }.forEach { task ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = task.isDone,
                                onClick = {
                                    taskModel.sendAction(Action.Insert(task.copy(isDone = !task.isDone)))
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Gray,
                                    unselectedColor = Color(note.textColor)
                                ),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(14.dp)
                            )
                            task.item?.let { item ->
                                Text(
                                    text = item,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 14.sp,
                                    style = TextStyle(
                                        textDecoration = if (task.isDone) {
                                            TextDecoration.LineThrough
                                        } else {
                                            TextDecoration.None
                                        },
                                        color = if (task.isDone) Color.Gray else Color(note.textColor)
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