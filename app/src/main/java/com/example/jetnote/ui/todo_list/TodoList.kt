package com.example.jetnote.ui.todo_list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
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
import com.example.jetnote.icons.DELETE_OUTLINE_ICON
import com.example.jetnote.cons.SURFACE_VARIANT
import com.example.jetnote.db.entities.note_and_todo.NoteAndTodo
import com.example.jetnote.db.entities.todo.Todo
import com.example.jetnote.fp.getMaterialColor
import com.example.jetnote.vm.NoteAndTodoVM
import com.example.jetnote.vm.TodoVM
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
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

    var itemState by remember { mutableStateOf("") }
    var idState by remember { mutableStateOf(-1L) }

    BottomSheetScaffold(
        modifier = Modifier.systemBarsPadding(),
        sheetContent = {
            Surface(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
            ) {
                OutlinedTextField(
                    value = itemState,
                    onValueChange = { itemState = it },
                    modifier = Modifier
                        .fillMaxWidth(),
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
                            if (observeTodoList.value.any { it.id == idState }) {
                                todoVM.updateTotoItem(Todo(idState, itemState, false))
                            } else {
                                Random.nextLong().let {
                                    todoVM.addTotoItem(
                                        Todo(it, itemState, false)
                                    )
                                    noteAndTodoVM.addNoteAndTodoItem(NoteAndTodo(noteUid, it))
                                }
                            }.invokeOnCompletion {
                                itemState = ""
                                idState = -1
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
    ) {
        Surface(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxSize()) {
            LazyColumn(
            ) {
                items(observeTodoList.value) { todo ->
                    if (observeNoteAndTodoList.value.contains(
                            NoteAndTodo(noteUid, todo.id)
                        )
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
                            Spacer(modifier = Modifier.width(20.dp))
                            todo.item?.let { item ->
                                ClickableText(
                                    text = AnnotatedString(item),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None,
                                        color = if (todo.isDone) Color.Gray else getMaterialColor(
                                            SURFACE_VARIANT
                                        )
                                    )
                                ) {
                                    itemState = todo.item!!
                                    idState = todo.id
                                }
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            Icon(painterResource(DELETE_OUTLINE_ICON), null,
                                modifier = Modifier.clickable {
                                    todoVM.deleteTotoItem(
                                        Todo(id = todo.id)
                                    )
                                })
                        }
                    }
                }
            }
        }
    }
}