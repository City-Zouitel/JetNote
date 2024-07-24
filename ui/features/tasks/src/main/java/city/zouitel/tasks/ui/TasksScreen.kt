package city.zouitel.tasks.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonIcons.DELETE_OUTLINE_ICON
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState
import kotlin.random.Random
import city.zouitel.tasks.model.NoteAndTask as InNoteAndTask
import city.zouitel.tasks.model.Task as InTask

@Suppress("IMPLICIT_CAST_TO_ANY")
data class TasksScreen(val id: String = CommonConstants.NONE): Screen {

    @Composable
    override fun Content() {

        Tasks(
            taskModel = getScreenModel<TaskScreenModel>(),
            noteAndTaskModel = getScreenModel<NoteAndTaskScreenModel>()
        )
    }

    @OptIn(
        ExperimentalComposeUiApi::class,
        ExperimentalMaterial3Api::class
    )
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun Tasks(
        taskModel: TaskScreenModel,
        noteAndTaskModel: NoteAndTaskScreenModel
    ) {
        val keyboardManager = LocalFocusManager.current
        val navigator = LocalNavigator.current

        val observeTaskList by remember(taskModel, taskModel::getAllTaskList).collectAsState()
        val observeNoteAndTodoList by remember(
            noteAndTaskModel,
            noteAndTaskModel::getAllNotesAndTask
        ).collectAsState()
        val uiState by remember(taskModel, taskModel::uiState).collectAsState()

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
                items(observeTaskList) { task ->
                    if (observeNoteAndTodoList.contains(
                            InNoteAndTask(id, task.id)
                        )
                    ) {
                        TaskItem(
                            task = task,
                            taskModel = taskModel,
                            onSwipe = {
                                taskModel.deleteTotoItem(
                                    InTask(id = task.id)
                                )
                            },
                            onClick = {
                                taskModel
                                    .updateId(task.id)
                                    .updateItem(task.item ?: "")
                            }
                        )
                    }
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .onFocusEvent { keyboardManager.moveFocus(FocusDirection.Enter) },
                        value = uiState.currentItem,
                        onValueChange = { taskModel.updateItem(it) },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 19.sp,
                            color = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surface)
                        ),
                        placeholder = {
                            Text(
                                text = "Task..",
                                maxLines = 1,
                                style = TextStyle(
                                    fontSize = 19.sp,
                                    color = Color.DarkGray
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (observeTaskList.any { it.id == uiState.currentId }) {
                                    taskModel.updateTotoItem(
                                        InTask(
                                            uiState.currentId,
                                            uiState.currentItem,
                                            false
                                        )
                                    )
                                } else {
                                    Random.nextLong().let {
                                        taskModel.addTotoItem(
                                            InTask(it, uiState.currentItem, false)
                                        )
                                        noteAndTaskModel.addNoteAndTaskItem(InNoteAndTask(id, it))
                                    }
                                }.also {
                                    taskModel.updateId()
                                        .updateItem()
                                }
                            }
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.DarkGray,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun TaskItem(
        task: InTask,
        taskModel: TaskScreenModel,
        onSwipe: () -> Unit,
        onClick: () -> Unit,
    ) {
        val swipeState = rememberSwipeableActionsState()
        val action = SwipeAction(
            onSwipe = {
                onSwipe.invoke()
            },
            icon = {
                Icon(painterResource(DELETE_OUTLINE_ICON), null)
            },
            background = Color.Red
        )

        SwipeableActionsBox(
            modifier = Modifier,
            backgroundUntilSwipeThreshold = Color.Transparent,
            endActions = listOf(action),
            swipeThreshold = 100.dp,
            state = swipeState
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick.invoke() },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = {
                        taskModel.updateTotoItem(
                            InTask(
                                id = task.id,
                                item = task.item,
                                isDone = !task.isDone
                            )
                        )
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.width(16.dp))

                task.item?.let { item ->

                    ClickableText(
                        text = AnnotatedString(item),
                        style = TextStyle(
                            fontSize = 18.sp,
                            textDecoration =
                            if (task.isDone) {
                                TextDecoration.LineThrough
                            } else {
                                TextDecoration.None
                            },
                            color = if (task.isDone) Color.Gray else MaterialTheme.colorScheme.onSurface
                        )
                    ) { onClick.invoke() }
                }
            }
        }
    }
}