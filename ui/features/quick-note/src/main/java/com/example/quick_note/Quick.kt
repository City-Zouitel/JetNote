package com.example.quick_note
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.Cons
import com.example.common_ui.Icons
import com.example.common_ui.MatColors
import com.example.common_ui.MatColors.Companion.ON_SURFACE
import com.example.common_ui.MatColors.Companion.OUT_LINE_VARIANT
import com.example.common_ui.MatColors.Companion.SURFACE
import com.example.local.model.Note
import java.util.UUID
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Quick(
    quickNoteVM: QuickNoteVM = hiltViewModel(),
    action:() -> (Unit)
    ) {
    val descriptionState = remember { mutableStateOf("") }

    val matColors = MatColors().getMaterialColor
    val backgroundColor = matColors(SURFACE).toArgb()
    val backgroundColorState = rememberSaveable { mutableStateOf(backgroundColor) }
    val textColor = contentColorFor(matColors(SURFACE)).toArgb()
    val textColorState = rememberSaveable { mutableStateOf(textColor) }
    val priorityState = remember { mutableStateOf(Cons.URGENT) }
    val focusRequester = FocusRequester()

    LaunchedEffect(Unit) {
        kotlin.runCatching {
            focusRequester.requestFocus()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = .6f))
            .clickable {
                action.invoke()
            }
    ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(start = 30.dp, end = 30.dp)
        ) {

            Surface(
                shape = RoundedCornerShape(18.dp),
                color = matColors(SURFACE)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.Transparent)
//                    .border(border = BorderStroke(0.dp, Color.Transparent), shape = CircleShape)
                ) {
                    OutlinedTextField(
                        value = descriptionState.value,
                        onValueChange = { descriptionState.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .focusRequester(focusRequester)
                            .onFocusEvent {
                            },
                        placeholder = {
                            Text("Note..", color = Color.Gray, fontSize = 22.sp)
                        },
                        textStyle = TextStyle(
                            fontSize = 21.sp,
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
                            }
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        SmallFloatingActionButton(
                            onClick = {
                                quickNoteVM.addQuickNote(
                                    Note(
                                        description = descriptionState.value,
                                        uid = UUID.randomUUID().toString(),
                                        color = backgroundColorState.value,
                                        textColor = textColorState.value,
                                        priority = priorityState.value
                                    )
                                ).invokeOnCompletion {
                                    action.invoke()
                                }

                            }, containerColor = matColors(OUT_LINE_VARIANT),
                            contentColor = contentColorFor(
                                backgroundColor = matColors(
                                    OUT_LINE_VARIANT
                                )
                            )
                        ) {
                            Icon(painterResource(Icons.DONE_ICON), null)
                        }
                    }
                }
            }
        }
    }
}