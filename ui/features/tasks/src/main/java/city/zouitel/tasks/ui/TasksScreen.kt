package city.zouitel.tasks.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.logic.events.UiEvent
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonSwipeItem
import city.zouitel.systemDesign.CommonTextField
import city.zouitel.tasks.model.NoteAndTask
import city.zouitel.tasks.model.Task
import kotlin.random.Random
import city.zouitel.tasks.model.NoteAndTask as InNoteAndTask
import city.zouitel.tasks.model.Task as InTask

@Suppress("IMPLICIT_CAST_TO_ANY")
data class TasksScreen(val id: String = CommonConstants.NONE): Screen {

    @Composable
    override fun Content() {

        Tasks(
            taskModel = getScreenModel(),
            noteAndTaskModel = getScreenModel()
        )
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter",
        "UnusedMaterialScaffoldPaddingParameter"
    )
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

        val focusRequester = remember { FocusRequester() }

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
                    .animateContentSize()
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 25.dp)
            ) {
                observeTaskList
                    .filter { observeNoteAndTodoList.contains(NoteAndTask(id, it.id)) }
                    .forEach { task ->
                        item {
                            CommonSwipeItem(
                                onClick = {
                                    taskModel
                                        .updateId(task.id)
                                        .updateItem(task.item ?: "")
                                },
                                onSwipeLeft = {
                                    taskModel.sendUiEvent(UiEvent.Delete(Task(id = task.id)))
                                },
                                onSwipeRight = {
                                    taskModel.sendUiEvent(UiEvent.Update(Task(id = task.id, item = task.item, isDone = !task.isDone)))
                                }
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = task.isDone,
                                        onCheckedChange = {
                                            taskModel.sendUiEvent(UiEvent.Update(Task(id = task.id, item = task.item, isDone = !task.isDone)))

                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color.Gray
                                        )
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    task.item?.let { item ->

                                        BasicText(
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
                                        )
                                    }
                                }
                            }
                        }
                    }

                item {
                    CommonTextField(
                        value = uiState.currentItem,
                        onValueChange = { taskModel.updateItem(it) },
                        receiver = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusEvent { keyboardManager.moveFocus(FocusDirection.Enter) },
                        placeholder = "Task..",
                        imeAction = ImeAction.Done,
                        keyboardActions = KeyboardActions {

                            if (observeTaskList.any { it.id == uiState.currentId }) {
                                taskModel.sendUiEvent(UiEvent.Update(InTask(uiState.currentId, uiState.currentItem, false)))
                            } else {
                                Random.nextLong().let {
                                    taskModel.sendUiEvent(UiEvent.Insert(InTask(it, uiState.currentItem, false)))
                                    noteAndTaskModel.sendUiEvent(UiEvent.Insert(InNoteAndTask(id, it)))
                                }
                            }
                            taskModel.updateId().updateItem()
                        }
                    )
                }
            }
        }
    }
}