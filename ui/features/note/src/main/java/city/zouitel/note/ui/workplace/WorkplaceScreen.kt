package city.zouitel.note.ui.workplace

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.text.format.DateFormat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastLastOrNull
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import city.zouitel.audios.model.NoteAndAudio
import city.zouitel.audios.ui.component.AudioScreenModel
import city.zouitel.audios.ui.component.BasicAudioScreen
import city.zouitel.audios.ui.component.NoteAndAudioScreenModel
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.CacheLinks
import city.zouitel.links.ui.LinkCard
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.links.ui.NoteAndLinkScreenModel
import city.zouitel.logic.findUrlLink
import city.zouitel.media.model.Media
import city.zouitel.media.model.NoteAndMedia
import city.zouitel.media.ui.MediaScreen
import city.zouitel.media.ui.MediaScreenModel
import city.zouitel.media.ui.NoteAndMediaScreenModel
import city.zouitel.note.model.Data
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.ui.bottom_bar.AddEditBottomBar
import city.zouitel.notifications.viewmodel.NotificationScreenModel
import city.zouitel.recoder.ui.RecorderScreen
import city.zouitel.reminder.ui.RemindingNote
import city.zouitel.note.utils.TextField
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.SoundEffect
import city.zouitel.tags.model.NoteAndTag
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.Task
import city.zouitel.tasks.ui.NoteAndTaskScreenModel
import city.zouitel.tasks.ui.TaskScreenModel
import java.util.Date
import java.util.UUID
import kotlin.random.Random

data class WorkplaceScreen(
    val id: String = UUID.randomUUID().toString(),
    val isNew: Boolean = true,
    val title: String? = null,
    val description: String? = null,
    val backgroundColor: Int = 0,
    val textColor: Int = 0,
    val priority: String = CommonConstants.NON,
    val reminding: Long = 0
): Screen {

    @Composable
    override fun Content() {
        val workspaceModel = getScreenModel<WorkplaceScreenModel>()

        LaunchedEffect(!isNew) {
            workspaceModel.updateTextColor(textColor)
                .updateBackgroundColor(backgroundColor)
                .updatePriority(priority)
        }

        Workplace(
            notificationModel = getScreenModel(),
            dataModel = getScreenModel(),
            tagModel = getScreenModel(),
            noteAndTagModel = getScreenModel(),
            taskModel = getScreenModel(),
            noteAndTodoModel = getScreenModel(),
            linkModel = getScreenModel(),
            noteAndLinkModel = getScreenModel(),
            dataStoreModel = getScreenModel(),
            audioModel = getScreenModel(),
            noteAndAudioModel = getScreenModel(),
            mediaModel = getScreenModel(),
            noteAndMediaModel = getScreenModel(),
            workspaceModel = workspaceModel
        )
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun Workplace(
        notificationModel: NotificationScreenModel,
        dataModel: DataScreenModel,
        tagModel: TagScreenModel,
        noteAndTagModel: NoteAndTagScreenModel,
        taskModel: TaskScreenModel,
        noteAndTodoModel: NoteAndTaskScreenModel,
        linkModel: LinkScreenModel,
        noteAndLinkModel: NoteAndLinkScreenModel,
        dataStoreModel: DataStoreScreenModel,
        audioModel: AudioScreenModel,
        noteAndAudioModel: NoteAndAudioScreenModel,
        mediaModel: MediaScreenModel,
        noteAndMediaModel: NoteAndMediaScreenModel,
        workspaceModel: WorkplaceScreenModel
    ) {
        val navigator = LocalNavigator.current
        val context = LocalContext.current
        val keyboardManager = LocalFocusManager.current

        val focusRequester by lazy { FocusRequester() }
        val sound by lazy { SoundEffect() }
        val dateState by lazy { mutableStateOf(Calendar.getInstance().time) }

        val titleState = rememberTextFieldState(title ?: "")
        val descriptionState = rememberTextFieldState(description ?: "")

        val thereIsSoundEffect by remember(
            dataStoreModel,
            dataStoreModel::getSound
        ).collectAsState()
        val observeNotesAndLabels by remember(
            noteAndTagModel,
            noteAndTagModel::getAllNotesAndTags
        ).collectAsState()
        val observeLabels by remember(tagModel, tagModel::getAllLTags).collectAsState()
        val observeTodoList by remember(
            taskModel,
            taskModel::getAllTaskList
        ).collectAsState()
        val observeNoteAndTodo by remember(
            noteAndTodoModel,
            noteAndTodoModel::getAllNotesAndTask
        ).collectAsState()
        val observerLinks by remember(linkModel, linkModel::getAllLinks).collectAsState()
        val observerNoteAndLink by remember(
            noteAndLinkModel,
            noteAndLinkModel::getAllNotesAndLinks
        ).collectAsState()
        val observerAudios by remember(audioModel, audioModel::allAudios).collectAsState()
        val observerNoteAndAudio by remember(noteAndAudioModel, noteAndAudioModel::allNoteAndAudio)
            .collectAsState()

        val uiState by remember(workspaceModel, workspaceModel::uiState).collectAsState()

        val filteredObservedTags by remember {
            derivedStateOf {
                observeLabels.filter {
                    observeNotesAndLabels.contains(
                        NoteAndTag(id, it.id)
                    )
                }
            }
        }

        val chooseImageLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
                uris.forEach { uri ->
                    context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    mediaInsert(mediaModel, noteAndMediaModel)
                }
            }

        LaunchedEffect(Unit) {
            kotlin.runCatching {
//                focusRequester.requestFocus()
            }
        }

        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.outlineVariant,
                    contentColor = contentColorFor(
                        backgroundColor = MaterialTheme.colorScheme.outlineVariant
                    ),
                    onClick = {
                        sound.makeSound.invoke(
                            context,
                            CommonConstants.KEY_STANDARD,
                            thereIsSoundEffect
                        )

                        if (isNew) {
                            dataModel.addData(
                                Data(
                                    uid = id,
                                    title = titleState.text.toString(),
                                    description = descriptionState.text.toString(),
                                    priority = uiState.priority,
                                    reminding = uiState.reminding,
                                    date = dateState.value.toString(),
                                    color = uiState.backgroundColor,
                                    textColor = uiState.textColor
                                )
                            )
                        } else {
                            dataModel.editData(
                                Data(
                                    uid = id,
                                    title = titleState.text.toString(),
                                    description = descriptionState.text.toString(),
                                    priority = uiState.priority,
                                    reminding = uiState.reminding,
                                    date = dateState.value.toString(),
                                    removed = 0,
                                    color = uiState.backgroundColor,
                                    textColor = uiState.textColor
                                )
                            )
                        }
                        navigator?.pop()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = if (isNew) CommonIcons.DONE_ICON else CommonIcons.EDIT_ICON),
                        null
                    )
                }
            },
            bottomBar = {
                AddEditBottomBar(
                    id = id,
                    dataStoreModel = dataStoreModel,
                    imageLaunch = chooseImageLauncher,
                    workspaceModel = workspaceModel,
                    titleState = titleState,
                    descriptionState = descriptionState,
                )
            }
        ) {
            // recording dialog visibility.
            if (uiState.recordedDialogState) {
                Navigator(RecorderScreen(id) { workspaceModel.updateRecorderDialog(false) })
            }

            // reminding dialog visibility.
            if (uiState.remindingDialogState) {
                RemindingNote(
                    dataStoreModel = dataStoreModel,
                    notificationModel = notificationModel,
                    title = titleState.text.toString(),
                    message = descriptionState.text.toString(),
                    uid = id,
                    dialogState = { state ->
                        workspaceModel.updateRemindingDialog(state)
                    },
                    remindingValue = { value ->
                        workspaceModel.updateRemindingValue(value)
                    }
                )
            }

            LazyColumn(
                Modifier
                    .background(Color(uiState.backgroundColor))
                    .fillMaxSize()
            ) {

                // display the image.
                item {
                    Navigator(MediaScreen(id = id, backgroundColor = uiState.backgroundColor))
                }

                // The Title.
                item {
                    TextField(
                        state = titleState,
                        receiver = mediaInsert(mediaModel, noteAndMediaModel),
                        modifier = Modifier
                            .height(50.dp)
                            .focusRequester(focusRequester)
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
                    )
                }

                // The Description.
                item {
                    TextField(
                        state = descriptionState,
                        receiver = mediaInsert(mediaModel, noteAndMediaModel),
                        modifier = Modifier
                            .height(200.dp)
                            .onFocusEvent {
                                workspaceModel.updateDescriptionFieldFocused(it.isFocused)
                            },
                        placeholder = "Note",
                        textColor = Color(uiState.textColor)
                    )
                }

                //display the audio player.
                item {
                    Spacer(modifier = Modifier.height(18.dp))

                    observerAudios.filter {
                        observerNoteAndAudio.contains(
                            NoteAndAudio(id, it.id)
                        )
                    }.fastLastOrNull { _audio ->
                        Navigator(screen = BasicAudioScreen(id, _audio))
                        true
                    }
                }

                // Link display.
                item {
                    findUrlLink(descriptionState.text.toString())?.let { links ->
                        for (link in links) {
                            CacheLinks(
                                linkModel = linkModel,
                                noteId = id,
                                url = link
                            )
                        }
                    }
                    // for refresh this screen.
                    observerLinks.filter {
                        observerNoteAndLink.contains(
                            NoteAndLink(id, it.id)
                        )
                    }.forEach { _link ->
                        LinkCard(
                            linkScreenModel = linkModel,
                            noteAndLinkScreenModel = noteAndLinkModel,
                            noteUid = id,
                            isSwipe = true,
                            link = _link
                        )
                    }
                }

                // display reminding chip.
                if (uiState.reminding != 0L) {
                    item {
                        uiState.reminding.let {
                            runCatching {
                                ElevatedAssistChip(
                                    modifier = Modifier.padding(start = 15.dp),
                                    onClick = {},
                                    label = {
                                        Text(
                                            DateFormat.format("yyyy-MM-dd HH:mm", Date(it))
                                                .toString(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textDecoration = if (it < java.util.Calendar.getInstance().time.time) {
                                                TextDecoration.LineThrough
                                            } else {
                                                TextDecoration.None
                                            },
                                            color = MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    },
                                    leadingIcon = {
                                        if (it >= java.util.Calendar.getInstance().time.time) {
                                            Icon(
                                                painterResource(CommonIcons.BELL_RING_ICON),
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

                // display all added tags.
                item {
                    ContextualFlowRow(
                        itemCount = filteredObservedTags.size,
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
                                    tint = Color(filteredObservedTags[index].color),
                                    modifier = Modifier.size(10.dp)
                                )
                            },
                            label = {
                                filteredObservedTags[index].label?.let { it1 -> Text(it1) }
                            }
                        )
                    }
                }

                // display the tasks list.
                item {
                    observeTodoList.filter {
                        observeNoteAndTodo.contains(
                            NoteAndTask(id, it.id)
                        )
                    }.forEach { todo ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = todo.isDone,
                                onCheckedChange = {
                                    taskModel.updateTotoItem(
                                        Task(
                                            id = todo.id,
                                            item = todo.item,
                                            isDone = !todo.isDone
                                        )
                                    )
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.Gray,
                                    uncheckedColor = Color(uiState.textColor)
                                )
                            )

                            todo.item?.let { item ->
                                Text(
                                    text = item,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None,
                                        color = if (todo.isDone) Color.Gray else Color(uiState.textColor)
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

    private fun mediaInsert(
        mediaModel: MediaScreenModel,
        noteAndMediaModel: NoteAndMediaScreenModel
    ): (Uri) -> Unit = { uri ->
        Random.nextLong().let {
            mediaModel.addMedia(Media(id = it, path = uri.toString()))
            noteAndMediaModel.addNoteAndMedia(NoteAndMedia(id, it))
        }
    }
}