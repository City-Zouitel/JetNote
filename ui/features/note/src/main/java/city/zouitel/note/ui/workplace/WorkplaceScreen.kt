package city.zouitel.note.ui.workplace

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.net.Uri
import android.text.format.DateFormat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.AssistChip
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastFirstOrNull
import androidx.core.net.toUri
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
import city.zouitel.logic.getImgPath
import city.zouitel.note.model.Data
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.ui.bottom_bar.AddEditBottomBar
import city.zouitel.notifications.viewmodel.NotificationScreenModel
import city.zouitel.recoder.ui.RecorderScreen
import city.zouitel.reminder.ui.RemindingNote
import city.zouitel.systemDesign.CommonTextField
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.ImageDisplayed
import city.zouitel.systemDesign.SoundEffect
import city.zouitel.systemDesign.findUrlLink
import city.zouitel.tags.model.NoteAndTag
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.Task
import city.zouitel.tasks.ui.NoteAndTaskScreenModel
import city.zouitel.tasks.ui.TaskScreenModel
import com.google.accompanist.flowlayout.FlowRow
import java.io.File
import java.util.Date
import java.util.UUID

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
            notificationModel = getScreenModel<NotificationScreenModel>(),
            dataModel = getScreenModel<DataScreenModel>(),
            tagModel = getScreenModel<TagScreenModel>(),
            noteAndTagModel = getScreenModel<NoteAndTagScreenModel>(),
            taskModel = getScreenModel<TaskScreenModel>(),
            noteAndTodoModel = getScreenModel<NoteAndTaskScreenModel>(),
            linkModel = getScreenModel<LinkScreenModel>(),
            noteAndLinkModel = getScreenModel<NoteAndLinkScreenModel>(),
            dataStoreModel = getScreenModel<DataStoreScreenModel>(),
            audioModel = getScreenModel<AudioScreenModel>(),
            noteAndAudioModel = getScreenModel<NoteAndAudioScreenModel>(),
            workspaceModel = workspaceModel
        )
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalFoundationApi::class)
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
        workspaceModel: WorkplaceScreenModel
    ) {
        val navigator = LocalNavigator.current
        val context = LocalContext.current
        val keyboardManager = LocalFocusManager.current
        val internalPath = context.filesDir.path

        val focusRequester by lazy { FocusRequester() }
        val sound by lazy { SoundEffect() }
        val imageFile by lazy { id getImgPath context }
        val dateState by lazy { mutableStateOf(Calendar.getInstance().time) }
        val bitImg by lazy { BitmapFactory.decodeFile(imageFile) }

        val titleState = rememberTextFieldState(title ?: "")
        val descriptionState = rememberTextFieldState(description ?: "")

        val photoState = remember { mutableStateOf<Bitmap?>(bitImg) }

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

        var imageUriState by remember { mutableStateOf<Uri?>(File(imageFile).toUri()) }
        val img by rememberSaveable { mutableStateOf(photoState) }

        val chooseImageLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
                imageUriState = it
                dataModel::decodeBitmapImage.invoke(img, photoState, it!!, context)
                img.value = photoState.value
                dataModel::saveImageLocally.invoke(
                    img.value,
                    "$internalPath/${CommonConstants.IMG_DIR}",
                    "$id.${CommonConstants.JPEG}"
                )
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
                                    trashed = 0,
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
                    ImageDisplayed(media = img.value?.asImageBitmap())
                }

                // The Title.
                item {
                    CommonTextField(
                        state = titleState,
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
                        keyboardActions = KeyboardActions(
                            onNext = { keyboardManager.moveFocus(FocusDirection.Next) }
                        )
                    )
                }

                // The Description.
                item {
                    CommonTextField(
                        state = descriptionState,
                        modifier = Modifier
                            .height(200.dp)
                            .onFocusEvent {
                                workspaceModel.updateDescriptionFieldFocused(it.isFocused)
                            },
                        placeholder = "Note",
                        textColor = Color(uiState.textColor)
                    )
                }

                //display the media player.
                item {
                    Spacer(modifier = Modifier.height(18.dp))

                    observerAudios.filter {
                        observerNoteAndAudio.contains(
                            NoteAndAudio(id, it.id)
                        )
                    }.fastFirstOrNull { _audio ->
                        Navigator(screen = BasicAudioScreen(id, _audio))
                        true
                    }
                }

                // Link display.
                item {
                    findUrlLink(descriptionState.text.toString())?.let { links ->
                        for (link in links) {
                            CacheLinks(
                                linkScreenModel = linkModel,
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
                            swipeable = true,
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
                    FlowRow {
                        observeLabels.filter {
                            observeNotesAndLabels.contains(
                                NoteAndTag(id, it.id)
                            )
                        }.forEach {
                            AssistChip(
                                onClick = { },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = CommonIcons.CIRCLE_ICON_18),
                                        contentDescription = null,
                                        tint = Color(it.color),
                                        modifier = Modifier.size(10.dp)
                                    )
                                },
                                label = {
                                    it.label?.let { it1 -> Text(it1) }
                                }
                            )
                        }
                    }
                }

                // display the todo list.
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
}