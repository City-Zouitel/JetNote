package city.zouitel.note.ui.workplace

import android.annotation.SuppressLint
import android.text.format.DateFormat
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.audio.ui.component.AudioScreenModel
import city.zouitel.audio.ui.component.BasicAudioScreen
import city.zouitel.audio.ui.record.RecordsScreen
import city.zouitel.domain.utils.Action
import city.zouitel.links.ui.LinkCard
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.media.ui.MediaScreen
import city.zouitel.note.model.Data
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.ui.bottom_bar.BottomBar
import city.zouitel.note.ui.utils.TextField
import city.zouitel.notifications.viewmodel.AlarmManagerScreenModel
import city.zouitel.reminder.ui.ReminderScreenModel
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonIcons.BELL_RING_ICON
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.tags.model.NoteAndTag
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel
import city.zouitel.tasks.ui.TaskScreenModel
import com.skydoves.cloudy.cloudy
import java.util.Date
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Suppress("DeferredResultUnused")
@OptIn(ExperimentalUuidApi::class)
data class WorkplaceScreen(
    val uid: String = Uuid.random().toString(),
    val isNew: Boolean = true,
    val title: String? = null,
    val description: String? = null,
    val backgroundColor: Int = 0,
    val textColor: Int = 0,
    val priority: Int = 0
): Screen {

    @Composable
    override fun Content() {
        val workspaceModel = getScreenModel<WorkplaceScreenModel>()

        LaunchedEffect(isNew) {
            workspaceModel.updateTextColor(textColor)
                .updateBackgroundColor(backgroundColor)
        }

        Workplace(
            dataModel = getScreenModel(),
            tagModel = getScreenModel(),
            noteAndTagModel = getScreenModel(),
            taskModel = getScreenModel(),
            linkModel = getScreenModel(),
            dataStoreModel = getScreenModel(),
            audioModel = getScreenModel(),
            reminderModel = getScreenModel(),
            alarmModel = getScreenModel(),
            workspaceModel = workspaceModel
        )
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
    @OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
    @Composable
    private fun Workplace(
        dataModel: DataScreenModel,
        tagModel: TagScreenModel,
        noteAndTagModel: NoteAndTagScreenModel,
        taskModel: TaskScreenModel,
        linkModel: LinkScreenModel,
        dataStoreModel: DataStoreScreenModel,
        audioModel: AudioScreenModel,
        reminderModel: ReminderScreenModel,
        alarmModel: AlarmManagerScreenModel,
        workspaceModel: WorkplaceScreenModel
    ) {
        LaunchedEffect(true) {
            linkModel.initializeUid(uid)
            audioModel.initializeUid(uid)
            reminderModel.initializeUid(uid)

            dataModel.sendAction(Action.Insert(Data(uid = uid)))
        }
        val keyboardManager = LocalFocusManager.current
        val navBottomSheet = LocalBottomSheetNavigator.current
        val haptic = LocalHapticFeedback.current
        val context = LocalContext.current

        val focus by lazy { FocusRequester() }

        val titleState = rememberTextFieldState(title ?: "")
        val descriptionState = rememberTextFieldState(description ?: "")

        val observeNotesAndLabels by remember(
            noteAndTagModel,
            noteAndTagModel::observeAll
        ).collectAsState()
        val observeLabels by remember(tagModel, tagModel::observeAll).collectAsState()
        val observeTodoList by remember(
            taskModel,
            taskModel::getAllTaskList
        ).collectAsState()
        val observerLinks by remember(linkModel, linkModel::observeByUid).collectAsState()
        val observerAudios by remember(audioModel, audioModel::observeByUid).collectAsState()
        val observeAllReminders by remember(reminderModel, reminderModel::observeAllById).collectAsState()
        val uiState by remember(workspaceModel, workspaceModel::uiState).collectAsState()
        val priorityState = remember { mutableIntStateOf(priority) }

        val filteredTags by remember {
            derivedStateOf {
                observeLabels.filter {
                    observeNotesAndLabels.contains(NoteAndTag(uid, it.id))
                }
            }
        }

        LaunchedEffect(true) {
            if (isNew) focus.requestFocus()
            taskModel.initializeTasks(uid)
        }

        Scaffold(
            modifier = Modifier
                .navigationBarsPadding()
                .cloudy(enabled = navBottomSheet.isVisible),
            bottomBar = {
                BottomBar(
                    isNew = isNew,
                    uid = uid,
                    titleState = titleState,
                    descriptionState = descriptionState,
                    dataStoreModel = dataStoreModel,
                    dataModel = dataModel,
                    linkModel = linkModel,
                    workspaceModel = workspaceModel,
                    priorityState = priorityState
                )
            }
        ) {
            LazyColumn(
                Modifier
                    .background(Color(uiState.backgroundColor))
                    .fillMaxSize()
            ) {

                // medias display.
                item {
                    Navigator(
                        MediaScreen(
                            uid = uid,
                            backgroundColor = uiState.backgroundColor,
                        )
                    )
                }

                // The Title.
                item {
                    TextField(
                        state = titleState,
                        modifier = Modifier
                            .height(50.dp)
                            .focusRequester(focus)
                            .onFocusEvent {
                                workspaceModel.updateTitleFieldFocused(it.isFocused)
                            },
                        placeholder = "Title",
                        textSize = 24.sp,
                        textColor = Color(uiState.textColor),
                        imeAction = ImeAction.Next,
                        keyboardAction = {
                            keyboardManager.moveFocus(FocusDirection.Next)
                        }
                    ) {
                        workspaceModel.handleContentReceiver(it, uid)
                    }
                }

                // The Description.
                item {
                    TextField(
                        state = descriptionState,
                        modifier = Modifier
                            .height(200.dp)
                            .onFocusEvent {
                                workspaceModel.updateDescriptionFieldFocused(it.isFocused)
                            },
                        placeholder = "Note",
                        textColor = Color(uiState.textColor)
                    ) {
                        workspaceModel.handleContentReceiver(it, uid)
                    }
                }

                //display the audio player.
                item {
                    Spacer(modifier = Modifier.height(18.dp))
                    when {
                        observerAudios == null -> {}
                        observerAudios?.id == 0L -> {}
                        else -> {
                            Navigator(screen = BasicAudioScreen(observerAudios!!))
                        }
                    }
                }

                //
                item {
                 Navigator(RecordsScreen(uid))
                }

                // Link display.
                item {
                    // for refresh this screen.
                    observerLinks.forEach { _link ->
                        LinkCard(isSwipe = true, link = _link)
                    }
                }

                // display reminder chip.
                item {
                    ContextualFlowRow(
                        itemCount = observeAllReminders.size,
                        modifier = Modifier
                            .animateContentSize()
                            .padding(3.dp)
                    ) { index ->
                        ElevatedAssistChip(
                            modifier = Modifier.padding(start = 8.dp),
                            onClick = {},
                            label = {
                                Text(
                                    modifier = Modifier
                                        .animateContentSize()
                                        .combinedClickable(onLongClick = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            reminderModel.sendAction(
                                                Action.DeleteById(observeAllReminders[index].id)
                                            )
                                            alarmModel.cancelAlarm(observeAllReminders[index].id)
                                        }) {},
                                    text = DateFormat.format(
                                        "yy/MM/dd HH:mm",
                                        Date(observeAllReminders.getOrNull(index)?.atTime ?: 0)
                                    ).toString(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textDecoration = if (observeAllReminders.getOrNull(index)?.isPassed == true) TextDecoration.LineThrough else TextDecoration.None,
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                )
                            },
                            leadingIcon = {
                                if (observeAllReminders.getOrNull(index)?.isPassed == false)
                                    Icon(
                                        painterResource(BELL_RING_ICON),
                                        null,
                                        tint = MaterialTheme.colorScheme.surfaceVariant
                                    )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color(.6f, .6f, .6f, .5f)
                            ),
                            elevation = AssistChipDefaults.assistChipElevation()
                        )
                    }
                }

                // display the tags.
                item {
                    ContextualFlowRow(
                        itemCount = filteredTags.size,
                        modifier = Modifier
                            .animateContentSize()
                            .padding(5.dp)
                    ) { index ->
                        ElevatedAssistChip(
                            modifier = Modifier
                                .alpha(.7f)
                                .padding(1.5.dp),
                            onClick = { },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = CommonIcons.CIRCLE_ICON_18),
                                    contentDescription = null,
                                    tint = Color(filteredTags.getOrNull(index)?.color ?: 0),
                                    modifier = Modifier.size(10.dp)
                                )
                            },
                            label = {
                                Text(
                                    modifier = Modifier
                                        .combinedClickable(onLongClick = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            noteAndTagModel.sendAction(
                                                Action.DeleteById(
                                                    filteredTags.getOrNull(index)?.id ?: 0
                                                )
                                            )
                                        }) {},
                                    text = filteredTags.getOrNull(index)?.label ?: ""
                                )
                            }
                        )
                    }
                }

                // display the tasks list.
                item {
                    observeTodoList.forEach { task ->
                        Row(
                            modifier = Modifier.animateContentSize().fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = task.isDone,
                                onCheckedChange = {
                                    taskModel.sendAction(Action.Insert(task.copy(id = task.id, isDone = it)))
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.Gray,
                                    uncheckedColor = Color(uiState.textColor)
                                )
                            )

                            task.item?.let { item ->
                                Text(
                                    text = item,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
                                        color = if (task.isDone) Color.Gray else Color(uiState.textColor)
                                    )
                                )
                            }
                        }
                    }
                }

                // void space.
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
            }
        }
    }
}