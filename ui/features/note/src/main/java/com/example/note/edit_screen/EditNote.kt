package com.example.note.edit_screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.common_ui.*
import com.example.common_ui.Cons.AUDIOS
import com.example.common_ui.Cons.HOME_ROUTE
import com.example.common_ui.Cons.IMAGES
import com.example.common_ui.Cons.JPEG
import com.example.common_ui.Cons.KEY_STANDARD
import com.example.common_ui.Cons.MP3
import com.example.common_ui.Cons.NUL
import com.example.common_ui.Icons.CIRCLE_ICON_18
import com.example.common_ui.Icons.EDIT_ICON
import com.example.common_ui.MatColors.Companion.OUT_LINE_VARIANT
import com.example.links.CacheLinks
import com.example.links.LinkPart
import com.example.links.LinkVM
import com.example.links.NoteAndLinkVM
import com.example.local.model.*
import com.example.note.UrlCard
import com.example.note.bottom_bar.AddEditBottomBar
import com.example.record.RecordingNote
import com.example.note.NoteVM
import com.google.accompanist.flowlayout.FlowRow
import java.io.File

@SuppressLint(
    "UnrememberedMutableState",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun NoteEdit(
    navController: NavController,
    noteVM: NoteVM = hiltViewModel(),
    exoViewModule: com.example.media_player.MediaPlayerVM = hiltViewModel(),
    noteAndLabelVM: com.example.tags.NoteAndLabelVM = hiltViewModel(),
    labelVM: com.example.tags.LabelVM = hiltViewModel(),
    todoVM: com.example.tasks.TodoVM = hiltViewModel(),
    noteAndTodoVM: com.example.tasks.NoteAndTodoVM = hiltViewModel(),
    dataStoreVM: DataStoreVM = hiltViewModel(),
    linkVM: LinkVM = hiltViewModel(),
    noteAndLinkVM: NoteAndLinkVM = hiltViewModel(),
    uid:String,
    title:String?,
    description:String?,
    color: Int,
    textColor: Int,
    priority: String,
    audioDuration:Int,
    reminding: Long
) {

    val ctx = LocalContext.current
    val internalPath = ctx.filesDir.path
    val keyboardManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()

    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val isTitleFieldFocused = remember { mutableStateOf(false) }
    val isDescriptionFieldFocused = remember { mutableStateOf(false) }

    val observeNotesAndLabels = remember(noteAndLabelVM,noteAndLabelVM::getAllNotesAndLabels).collectAsState()
    val observeLabels = remember(labelVM, labelVM::getAllLabels).collectAsState()

    val observerLinks = remember(linkVM, linkVM::getAllLinks).collectAsState()
    val observerNoteAndLink =
        remember(noteAndLinkVM, noteAndLinkVM::getAllNotesAndLinks).collectAsState()

    val observeTodoList = remember(todoVM, todoVM::getAllTodoList).collectAsState()
    val observeNoteAndTodo =
        remember(noteAndTodoVM, noteAndTodoVM::getAllNotesAndTodo).collectAsState()

    val getMatColor = MatColors().getMaterialColor
    val sound = SoundEffect()

    val titleState = rememberSaveable {
        mutableStateOf(
            if(title == NUL || title.isNullOrEmpty()) null else decodeUrl.invoke(title)
        )
    }
//        .filterBadWords()
//        .filterBadEmoji()

    val descriptionState = rememberSaveable {
        mutableStateOf(
            if(description == NUL) null else decodeUrl.invoke(description)
        )
    }
//        .filterBadWords()
//        .filterBadEmoji()

    val backgroundColorState = rememberSaveable { mutableStateOf(color) }
    val textColorState = rememberSaveable { mutableStateOf(textColor) }

    val priorityState = remember { mutableStateOf(priority) }

    val mediaFile = "$internalPath/$AUDIOS/$uid.$MP3"

    //
    val dateState = mutableStateOf(Calendar.getInstance().time)

    val remindingDialogState = remember { mutableStateOf(false) }

    val recordDialogState = remember { mutableStateOf(false) }

    val imagePath = "$internalPath/$IMAGES/$uid.$JPEG"
    val bitImg = BitmapFactory.decodeFile(imagePath)

    val photoState = remember { mutableStateOf<Bitmap?>(bitImg) }
    val imageUriState = remember { mutableStateOf<Uri?>(File(imagePath).toUri()) }
    val img by rememberSaveable { mutableStateOf(photoState) }

    val chooseImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            imageUriState.value = it
            noteVM::decodeBitmapImage.invoke(img, photoState, it!!, ctx)
            img.value = photoState.value
            noteVM::saveImageLocally.invoke(
                img.value, "$internalPath/$IMAGES", "$uid.$JPEG"
            )
        }

    val sheetState = rememberBottomSheetScaffoldState()

    val remindingValue = remember { mutableStateOf(reminding) }

    val audioDurationState = remember { mutableStateOf(0) }
    val gifUri = remember { mutableStateOf<Uri?>(null) }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetPeekHeight = 50.dp,
        backgroundColor = Color(backgroundColorState.value),
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding(),
        floatingActionButton = {
            Column {
                AnimatedVisibility(visible = sheetState.bottomSheetState.isCollapsed) {
                    FloatingActionButton(
                        containerColor = getMatColor(OUT_LINE_VARIANT),
                        contentColor = contentColorFor(backgroundColor = getMatColor(OUT_LINE_VARIANT)),
                        onClick = {
                            sound.makeSound.invoke(ctx, KEY_STANDARD,thereIsSoundEffect.value)

                            noteVM.updateNote(
                                Note(
                                    title = titleState.value,
                                    description = descriptionState.value,
                                    priority = priorityState.value,
                                    uid = uid,
                                    audioDuration = audioDuration,
                                    reminding = remindingValue.value,
                                    date = dateState.value.toString(),
                                    trashed = 0,
                                    color = backgroundColorState.value,
                                    textColor = textColorState.value,
                                )
                            )
                            navController.navigate(HOME_ROUTE)
                        }) {
                        Icon(
                            painter = painterResource(id = EDIT_ICON),
                            null
                        )
                    }
                }
            }
        },
        sheetContent = {
            AddEditBottomBar(
                navController = navController,
                recordDialogState = recordDialogState,
                remindingDialogState = remindingDialogState,
                note = Note(uid = uid),
                backgroundColorState = backgroundColorState,
                textColorState = textColorState,
                priorityColorState = priorityState,
                notePriority = priorityState,
                imageLaunch = chooseImageLauncher,
                titleFieldState = titleState,
                descriptionFieldState = descriptionState,
                isTitleFieldSelected = isTitleFieldFocused,
                isDescriptionFieldSelected = isDescriptionFieldFocused,
                isCollapsed = sheetState
            )
        }) {

        // recording dialog visibility.
        if (recordDialogState.value) {
            RecordingNote(uid = uid, dialogState = recordDialogState)
        }

        // reminding dialog visibility.
        if (remindingDialogState.value) {
            com.example.reminder.RemindingNote(
                dialogState = remindingDialogState,
                remindingValue = remindingValue,
                title = titleState.value,
                message = descriptionState.value,
                uid = uid
            )
        }

        LazyColumn(Modifier.fillMaxSize()) {
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
                    com.example.media_player.NoteMediaPlayer(localMediaUid = uid)
                    audioDurationState.value =
                        exoViewModule.getMediaDuration(ctx,mediaFile).toInt()
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
                        color = Color(textColorState.value)
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
                        textColor = contentColorFor(backgroundColor = Color(backgroundColorState.value))
                    )
                )
            }

            //The Description.
            item {
                OutlinedTextField(
                    value = descriptionState.value ?: "",
                    onValueChange = { descriptionState.value = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text("Note", color = Color.Gray, fontSize = 19.sp)
                    },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Default,
                        color = Color(textColorState.value)
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

            //
            item {
                findUrlLink(descriptionState.value)?.let { url ->
                    CacheLinks(
                        linkVM = linkVM,
                        noteAndLinkVM = noteAndLinkVM,
                        noteUid = uid,
                        url = url
                    )

                }
                // for refresh this screen.

                observerLinks.value.filter {
                    observerNoteAndLink.value.contains(
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

            // display all added labels.
            item {
                FlowRow {
                    observeLabels.value.filter {
                        observeNotesAndLabels.value.contains(
                            NoteAndLabel(uid, it.id)
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
                observeTodoList.value.filter {
                    observeNoteAndTodo.value.contains(
                        NoteAndTodo(uid, it.id)
                    )
                }.forEach { todo ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = todo.isDone,
                            onCheckedChange = {
                                todoVM.updateTotoItem(
                                    Todo(
                                        id = todo.id,
                                        item = todo.item,
                                        isDone = !todo.isDone
                                    )
                                )
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Gray,
                                uncheckedColor = Color(textColorState.value)
                            )
                        )

                        todo.item?.let { item ->
                            Text(
                                text = item,
                                fontSize = 14.sp,
                                style = TextStyle(
                                    textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None,
                                    color = if (todo.isDone) Color.Gray else Color(textColorState.value)
                                )
                            )
                        }
                    }
                }
            }

//            // void space.
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp))
            }
        }
    }
}
