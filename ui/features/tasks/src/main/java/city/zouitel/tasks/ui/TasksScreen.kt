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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import city.zouitel.logic.events.UiEvents
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonSwipeItem
import city.zouitel.systemDesign.CommonTextField
import city.zouitel.tasks.model.Task
import kotlin.random.Random

data class TasksScreen(val uid: String): Screen {

    @Composable
    override fun Content() {
        val taskModel = getScreenModel<TaskScreenModel>()

        LaunchedEffect(true) {
            taskModel.initializeTasks(uid)
        }

        Tasks(taskModel = taskModel)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter",
        "UnusedMaterialScaffoldPaddingParameter"
    )
    @Composable
    private fun Tasks(taskModel: TaskScreenModel) {
        val keyboardManager = LocalFocusManager.current
        val navigator = LocalNavigator.current

        val observeTaskList by remember(taskModel, taskModel::getAllTaskList).collectAsStateWithLifecycle()
        val uiState by remember(taskModel, taskModel::uiState).collectAsStateWithLifecycle()

        val focusRequester by lazy { FocusRequester() }
        LaunchedEffect(Unit) { focusRequester.requestFocus() }

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
                observeTaskList.forEach { task ->
                        item {
                            CommonSwipeItem(
                                onClick = {
                                    taskModel
                                        .updateId(task.id)
                                        .updateItem(task.item ?: "")
                                },
                                onSwipeLeft = {
                                    taskModel.sendUiEvent(UiEvents.Delete(task.id))
                                },
                                onSwipeRight = {
                                    taskModel.sendUiEvent(UiEvents.Update(task.id))
                                }
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = task.isDone,
                                        onCheckedChange = {
                                            taskModel.sendUiEvent(UiEvents.Update(task.id))

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
                            val taskToUpdate = observeTaskList.find { it.id == uiState.currentId }
                            if (taskToUpdate != null) {
                                taskModel.sendUiEvent(UiEvents.Update(taskToUpdate.copy(item = uiState.currentItem)))
                            } else {
                                val newTaskId = Random.nextLong()
                                taskModel.sendUiEvent(UiEvents.Insert(Task(newTaskId, uid, uiState.currentItem)))
                            }
                            taskModel.updateId().updateItem()
                        }
                    )
                }
            }
        }
    }
}