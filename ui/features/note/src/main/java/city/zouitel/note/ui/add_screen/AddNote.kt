package city.zouitel.note.ui.add_screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import city.zouitel.audios.MediaPlayerViewModel
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.CacheLinks
import city.zouitel.links.ui.LinkPart
import city.zouitel.links.ui.LinkVM
import city.zouitel.links.ui.NoteAndLinkVM
import city.zouitel.note.DataViewModel
import city.zouitel.note.model.Data
import city.zouitel.note.ui.bottom_bar.AddEditBottomBar
import city.zouitel.recoder.ui.RecordingNote
import city.zouitel.reminder.ui.RemindingNote
import city.zouitel.systemDesign.Cons.AUDIOS
import city.zouitel.systemDesign.Cons.HOME_ROUTE
import city.zouitel.systemDesign.Cons.IMAGES
import city.zouitel.systemDesign.Cons.JPEG
import city.zouitel.systemDesign.Cons.KEY_STANDARD
import city.zouitel.systemDesign.Cons.MP3
import city.zouitel.systemDesign.Cons.NON
import city.zouitel.systemDesign.Cons.NUL
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.CIRCLE_ICON_18
import city.zouitel.systemDesign.Icons.DONE_ICON
import city.zouitel.systemDesign.ImageDisplayed
import city.zouitel.systemDesign.MaterialColors
import city.zouitel.systemDesign.MaterialColors.Companion.OUT_LINE_VARIANT
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE
import city.zouitel.systemDesign.SoundEffect
import city.zouitel.systemDesign.decodeUrl
import city.zouitel.systemDesign.findUrlLink
import city.zouitel.tags.model.NoteAndTag
import city.zouitel.tags.viewmodel.NoteAndTagViewModel
import city.zouitel.tags.viewmodel.TagViewModel
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.Task
import city.zouitel.tasks.viewmodel.NoteAndTaskViewModel
import city.zouitel.tasks.viewmodel.TaskViewModel
import com.google.accompanist.flowlayout.FlowRow
import org.koin.androidx.compose.koinViewModel
import java.io.File

@SuppressLint(
    "UnrememberedMutableState",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun NoteAdd(
    dataViewModel: DataViewModel = koinViewModel(),
    exoVM: MediaPlayerViewModel = koinViewModel(),
    noteAndTagViewModel: NoteAndTagViewModel = koinViewModel(),
    tagViewModel: TagViewModel = koinViewModel(),
    taskViewModel: TaskViewModel = koinViewModel(),
    noteAndTodoVM: NoteAndTaskViewModel = koinViewModel(),
    dataStoreVM: DataStoreVM = koinViewModel(),
    linkVM: LinkVM = koinViewModel(),
    noteAndLinkVM: NoteAndLinkVM = koinViewModel(),
    navController: NavController,
    uid: String,
    description: String?
) {
    val ctx = LocalContext.current
    val keyboardManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val internalPath = ctx.filesDir.path

    val focusRequester by lazy { FocusRequester() }
    val getMatColor by lazy { MaterialColors().getMaterialColor }
    val sound by lazy { SoundEffect() }
    val mediaFile by lazy { arrayOf(internalPath,'/',AUDIOS,'/',uid,'.',MP3).joinToString("") }
    val imageFile by lazy { arrayOf(internalPath,'/',IMAGES,'/',uid,'.',JPEG).joinToString("") }
    val dateState by lazy { mutableStateOf(Calendar.getInstance().time) }
    val bitImg by lazy { BitmapFactory.decodeFile(imageFile) }

    val isTitleFieldFocused = remember { mutableStateOf(false) }
    val isDescriptionFieldFocused = remember { mutableStateOf(false) }
    val titleState = rememberSaveable { mutableStateOf<String?>(null) }
    val backgroundColor = getMatColor(SURFACE).toArgb()
    val backgroundColorState = rememberSaveable { mutableIntStateOf(backgroundColor) }
    val textColor = contentColorFor(getMatColor(SURFACE)).toArgb()
    val textColorState = rememberSaveable { mutableIntStateOf(textColor) }
    val priorityState = remember { mutableStateOf(NON) }
    val photoState = remember { mutableStateOf<Bitmap?>(bitImg) }
    val remindingDialogState = remember { mutableStateOf(false) }
    val recordDialogState = remember { mutableStateOf(false) }
    val state = rememberBottomSheetScaffoldState()
    val remindingValue = remember { mutableLongStateOf(0L) }
    val audioDurationState = remember { mutableIntStateOf(0) }

    var focusState by remember { mutableStateOf(false) }
    val thereIsSoundEffect by remember(dataStoreVM, dataStoreVM::getSound).collectAsState()
    val observeNotesAndLabels by remember(noteAndTagViewModel, noteAndTagViewModel::getAllNotesAndTags).collectAsState()
    val observeLabels by remember(tagViewModel, tagViewModel::getAllLTags).collectAsState()
    val observeTodoList by remember(taskViewModel, taskViewModel::getAllTaskList).collectAsState()
    val observeNoteAndTodo by remember(noteAndTodoVM, noteAndTodoVM::getAllNotesAndTask).collectAsState()
    val observerLinks by remember(linkVM, linkVM::getAllLinks).collectAsState()
    val observerNoteAndLink by remember(noteAndLinkVM, noteAndLinkVM::getAllNotesAndLinks).collectAsState()
    var imageUriState by remember { mutableStateOf<Uri?>(File(imageFile).toUri()) }
    val img by rememberSaveable { mutableStateOf(photoState) }

    val descriptionState = rememberSaveable {
        mutableStateOf(
            if (description == NUL) null else decodeUrl(description)
        )
    }

    val chooseImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            imageUriState = it
            dataViewModel::decodeBitmapImage.invoke(img, photoState, it!!, ctx)
            img.value = photoState.value
            dataViewModel::saveImageLocally.invoke(
                img.value, "$internalPath/$IMAGES", "$uid.$JPEG"
            )
        }

    LaunchedEffect(Unit) {
        kotlin.runCatching {
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                containerColor = getMatColor(OUT_LINE_VARIANT),
                contentColor = contentColorFor(
                    backgroundColor = getMatColor(
                        OUT_LINE_VARIANT
                    )
                ),
                onClick = {
                    sound.makeSound.invoke(ctx, KEY_STANDARD, thereIsSoundEffect)

                    dataViewModel.addData(
                        Data(
                            title = if (titleState.value.isNullOrBlank()) null else titleState.value,
                            description = if (descriptionState.value.isNullOrBlank()) null else descriptionState.value,
                            priority = priorityState.value,
                            uid = uid,
                            reminding = remindingValue.longValue,
                            date = dateState.value.toString(),
                            audioDuration = audioDurationState.intValue,
                            color = backgroundColorState.intValue,
                            textColor = textColorState.intValue,
                        )
                    )
                    navController.navigate(HOME_ROUTE)
                }
            ) {
                Icon(
                    painter = painterResource(id = DONE_ICON),
                    null
                )
            }
        },
        bottomBar = {
            AddEditBottomBar(
                navController = navController,
                recordDialogState = recordDialogState,
                remindingDialogState = remindingDialogState,
                note = Data(uid = uid),
                backgroundColorState = backgroundColorState,
                textColorState = textColorState,
                priorityColorState = priorityState,
                notePriority = priorityState,
                imageLaunch = chooseImageLauncher,
                titleFieldState = titleState,
                descriptionFieldState = descriptionState,
                isTitleFieldSelected = isTitleFieldFocused,
                isDescriptionFieldSelected = isDescriptionFieldFocused,
                isCollapsed = state
            )
        }
    ) {
        // recording dialog visibility.
        if (recordDialogState.value) {
            RecordingNote(uid = uid, dialogState = recordDialogState)
        }

        // reminding dialog visibility.
        if (remindingDialogState.value) {
            RemindingNote(
                dialogState = remindingDialogState,
                remindingValue = remindingValue,
                title = titleState.value,
                message = descriptionState.value,
                uid = uid
            )
        }

        LazyColumn(
            Modifier
                .background(Color(backgroundColorState.intValue))
                .fillMaxSize()
        ) {

            // display the image.
            item {
                ImageDisplayed(media = img.value?.asImageBitmap())
            }

            // display the media player.
            item {
                Spacer(modifier = Modifier.height(18.dp))
                if (
                    File(mediaFile).exists() && !recordDialogState.value
                ) {
                    city.zouitel.audios.NoteMediaPlayer(localMediaUid = uid)
                    audioDurationState.intValue = exoVM.getMediaDuration(ctx, mediaFile).toInt()
                }
            }

            // The Title.
            item {
                OutlinedTextField(
                    value = titleState.value ?: "",
                    onValueChange = { titleState.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .focusRequester(focusRequester)
                        .onFocusEvent {
                            isTitleFieldFocused.value = it.isFocused
                        },
                    placeholder = {
                        Text("Title", color = Color.Gray, fontSize = 24.sp)
                    },
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Default,
                        color = Color(textColorState.intValue)
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            keyboardManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = contentColorFor(
                            backgroundColor = Color(
                                backgroundColorState.intValue
                            )
                        )
                    )
                )
            }

            // The Description.
            item {
                OutlinedTextField(
                    value = descriptionState.value ?: "",
                    onValueChange = {
                        descriptionState.value = it

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            isDescriptionFieldFocused.value = it.isFocused
                            focusState = it.isFocused
                        },
                    placeholder = {
                        Text("Note", color = Color.Gray, fontSize = 19.sp)
                    },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Default,
                        color = Color(textColorState.intValue)
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Default
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            keyboardManager.clearFocus()
                        }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }

            // Link display.
            item {
                findUrlLink(descriptionState.value)?.let { url ->
                    CacheLinks(
                        linkVM = linkVM,
                        noteAndLinkVM = noteAndLinkVM,
                        noteId = uid,
                        url = url
                    )
                }
                // for refresh this screen.
                observerLinks.filter {
                    observerNoteAndLink.contains(
                        NoteAndLink(uid, it.id)
                    )
                }.forEach { _link ->
                    LinkPart(
                        linkVM = linkVM,
                        noteAndLinkVM = noteAndLinkVM,
                        noteUid = uid,
                        swipeable = true,
                        link = _link
                    )
                }
            }

            // display all added tags.
            item {
                FlowRow {
                    observeLabels.filter {
                        observeNotesAndLabels.contains(
                            NoteAndTag(uid, it.id)
                        )
                    }.forEach {
                        AssistChip(
                            onClick = { },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = CIRCLE_ICON_18),
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
                        NoteAndTask(uid, it.id)
                    )
                }.forEach { todo ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = todo.isDone,
                            onCheckedChange = {
                                taskViewModel.updateTotoItem(
                                    Task(
                                        id = todo.id,
                                        item = todo.item,
                                        isDone = !todo.isDone
                                    )
                                )
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Gray,
                                uncheckedColor = Color(textColorState.intValue)
                            )
                        )

                        todo.item?.let { item ->
                            Text(
                                text = item,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None,
                                    color = if (todo.isDone) Color.Gray else Color(textColorState.intValue)
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