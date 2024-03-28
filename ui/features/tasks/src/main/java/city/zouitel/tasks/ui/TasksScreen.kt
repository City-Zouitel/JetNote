package city.zouitel.tasks.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.systemDesign.Cons
import city.zouitel.systemDesign.Icons.DELETE_OUTLINE_ICON
import city.zouitel.tasks.viewmodel.NoteAndTaskScreenModel
import city.zouitel.tasks.viewmodel.TaskScreenModel
import city.zouitel.tasks.model.NoteAndTask as InNoteAndTask
import city.zouitel.tasks.model.Task as InTask
import kotlinx.coroutines.Job
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState
import org.koin.core.component.KoinComponent
import kotlin.random.Random

data class TasksScreen(val id: String = Cons.NONE): Screen, KoinComponent {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val taskModel = getScreenModel<TaskScreenModel>()
        val noteAndTodoListModel = getScreenModel<NoteAndTaskScreenModel>()

        val observeTaskList = taskModel.getAllTaskList.collectAsState()
        val observeNoteAndTodoList = noteAndTodoListModel.getAllNotesAndTask.collectAsState()

        val itemState = remember { mutableStateOf("") }//.filterBadWords()
        val idState = remember { mutableStateOf(-1L) }

        Scaffold(
            modifier = Modifier.navigationBarsPadding()
        ) {
            LazyColumn(
                modifier = Modifier.padding(top = 25.dp)
            ) {
                items(observeTaskList.value) { task ->
                    if (observeNoteAndTodoList.value.contains(
                            InNoteAndTask(id, task.id)
                        )
                    ) {
                        TodoItem(task, taskModel, itemState, idState) {
                            taskModel.deleteTotoItem(
                                InTask(id = task.id)
                            )
                        }
                    }
                }
                item {
                    OutlinedTextField(
                        value = itemState.value,
                        onValueChange = { itemState.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        placeholder = {
                            Text("Task..", color = Color.Gray, fontSize = 19.sp)
                        },
                        maxLines = 1,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Default,
                        ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (observeTaskList.value.any { it.id == idState.value }) {
                                    taskModel.updateTotoItem(
                                        InTask(
                                            idState.value,
                                            itemState.value,
                                            false
                                        )
                                    )
                                } else {
                                    Random.nextLong().let {
                                        taskModel.addTotoItem(
                                            InTask(it, itemState.value, false)
                                        )
                                        noteAndTodoListModel.addNoteAndTaskItem(InNoteAndTask(id, it))
                                    }
                                }.invokeOnCompletion {
                                    itemState.value = ""
                                    idState.value = -1L
                                }
                            }
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }

    @Composable
    fun TodoItem(
        task: InTask,
        taskModel: TaskScreenModel,
        itemState: MutableState<String>,
        idState: MutableState<Long>,
        onClick: () -> Job
    ) {
        val swipeState = rememberSwipeableActionsState()
        val action = SwipeAction(
            onSwipe = {
                onClick.invoke()
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
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
                    ) {
                        itemState.value = task.item!!
                        idState.value = task.id
                    }
                }
            }
        }
    }
}