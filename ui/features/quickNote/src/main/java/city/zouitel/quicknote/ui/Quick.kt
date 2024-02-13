package city.zouitel.quicknote.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.CacheLinks
import city.zouitel.links.ui.LinkPart
import city.zouitel.links.ui.LinkVM
import city.zouitel.links.ui.NoteAndLinkVM
import city.zouitel.quicknote.model.QuickData
import city.zouitel.systemDesign.Cons
import city.zouitel.systemDesign.Icons
import city.zouitel.systemDesign.MaterialColors
import city.zouitel.systemDesign.MaterialColors.Companion.OUT_LINE_VARIANT
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE
import city.zouitel.systemDesign.findUrlLink
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Quick(
    quickDataViewModel: QuickDataViewModel = koinViewModel(),
    linkVM: LinkVM = koinViewModel(),
    noteAndLinkVM: NoteAndLinkVM = koinViewModel(),
    action:() -> (Unit)
) {
    val uid = UUID.randomUUID().toString()
    val descriptionState = remember { mutableStateOf("") }
    val materialColors = MaterialColors().getMaterialColor
    val backgroundColor = materialColors(SURFACE).toArgb()
    val backgroundColorState = rememberSaveable { mutableStateOf(backgroundColor) }
    val textColor = contentColorFor(materialColors(SURFACE)).toArgb()
    val textColorState = rememberSaveable { mutableStateOf(textColor) }
    val priorityState = remember { mutableStateOf(Cons.URGENT) }
    val focusRequester = FocusRequester()
    val observerLinks by remember(linkVM, linkVM::getAllLinks).collectAsState()
    val observerNoteAndLink by remember(noteAndLinkVM, noteAndLinkVM::getAllNotesAndLinks).collectAsState()

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
                color = materialColors(SURFACE)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .background(Color.Transparent)
//                    .border(border = BorderStroke(0.dp, Color.Transparent), shape = CircleShape)
                ) {
                    item {
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

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            SmallFloatingActionButton(
                                onClick = {
                                    quickDataViewModel.addQuickData(
                                        QuickData(
                                            description = descriptionState.value,
                                            uid = uid,
                                            color = backgroundColorState.value,
                                            textColor = textColorState.value,
                                            priority = priorityState.value
                                        )
                                    ).invokeOnCompletion {
                                        action.invoke()
                                    }

                                }, containerColor = materialColors(OUT_LINE_VARIANT),
                                contentColor = contentColorFor(
                                    backgroundColor = materialColors(
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
}