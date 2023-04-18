package com.example.tasks

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.Icons.DELETE_OUTLINE_ICON
import com.example.common_ui.MaterialColors
import com.example.common_ui.MaterialColors.Companion.ON_SURFACE
import com.example.common_ui.MaterialColors.Companion.SURFACE
import com.example.local.model.NoteAndTodo
import com.example.local.model.Todo
import kotlinx.coroutines.Job
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState
import kotlin.random.Random

private val getMatColor = MaterialColors().getMaterialColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodoList(
    todoVM: TodoVM = hiltViewModel(),
    noteAndTodoVM: NoteAndTodoVM = hiltViewModel(),
    noteUid:String
) {
    val observeTodoList = remember(todoVM, todoVM::getAllTodoList).collectAsState()
    val observeNoteAndTodoList =
        remember(noteAndTodoVM, noteAndTodoVM::getAllNotesAndTodo).collectAsState()

    val itemState = remember { mutableStateOf("") }//.filterBadWords()
    val idState = remember { mutableStateOf(-1L) }

    Scaffold(
        modifier = Modifier.navigationBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 25.dp)
        ) {
            items(observeTodoList.value) { todo ->
                if (observeNoteAndTodoList.value.contains(
                        NoteAndTodo(noteUid, todo.id)
                    )
                ) {
                    TodoItem(todo, todoVM, itemState, idState) {
                        todoVM.deleteTotoItem(
                            Todo(id = todo.id)
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
                        .background(getMatColor(SURFACE)),
                    placeholder = {
                        Text("Todo..", color = Color.Gray, fontSize = 19.sp)
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
                            if (observeTodoList.value.any { it.id == idState.value }) {
                                todoVM.updateTotoItem(Todo(idState.value, itemState.value, false))
                            } else {
                                Random.nextLong().let {
                                    todoVM.addTotoItem(
                                        Todo(it, itemState.value, false)
                                    )
                                    noteAndTodoVM.addNoteAndTodoItem(NoteAndTodo(noteUid, it))
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
    todo: Todo,
    todoVM: TodoVM,
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
                    checkedColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.width(16.dp))

            todo.item?.let { item ->

                ClickableText(
                    text = AnnotatedString(item),
                    style = TextStyle(
                        fontSize = 18.sp,
                        textDecoration =
                        if (todo.isDone) {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        },
                        color = if (todo.isDone) Color.Gray else getMatColor(
                            ON_SURFACE
                        )
                    )
                ) {
                    itemState.value = todo.item!!
                    idState.value = todo.id
                }
            }
        }
    }
}