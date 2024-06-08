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
import androidx.compose.runtime.mutableIntStateOf
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.ui.CacheLinks
import city.zouitel.links.ui.LinkCard
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.links.ui.NoteAndLinkScreenModel
import city.zouitel.logic.findUrlLink
import city.zouitel.quicknote.model.QuickData
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonIcons
import java.util.UUID

data class QuickScreen(
    val action: () -> (Unit)
): Screen {
    @Composable
    override fun Content() {
        val dataModel = getScreenModel<QuickDataScreenModel>()
        val linkModel = getScreenModel<LinkScreenModel>()
        val noteAndLinkModel = getScreenModel<NoteAndLinkScreenModel>()

        Quick(dataModel, linkModel, noteAndLinkModel, action = action)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Quick(
        dataModel: QuickDataScreenModel,
        linkModel: LinkScreenModel,
        noteAndLinkModel: NoteAndLinkScreenModel,
        action:() -> (Unit)
    ) {
        val uid = UUID.randomUUID().toString()

        val descriptionState = remember { mutableStateOf("") }
        val backgroundColor = MaterialTheme.colorScheme.surface.toArgb()
        val backgroundColorState = rememberSaveable { mutableIntStateOf(backgroundColor) }
        val textColor = contentColorFor(MaterialTheme.colorScheme.surface).toArgb()
        val textColorState = rememberSaveable { mutableIntStateOf(textColor) }
        val priorityState = remember { mutableStateOf(CommonConstants.NON) }

        val focusRequester = FocusRequester()

        val observerLinks by remember(linkModel, linkModel::getAllLinks).collectAsState()
        val observerNoteAndLink by remember(noteAndLinkModel, noteAndLinkModel::getAllNotesAndLinks).collectAsState()

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
                    color = MaterialTheme.colorScheme.surface
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .background(Color.Transparent)
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
                            findUrlLink(descriptionState.value) ?. let { links ->
                                for (link in links) {
                                    CacheLinks(
                                        linkModel = linkModel,
                                        noteId = uid,
                                        url = link
                                    )
                                }
                            }
                            // for refresh this screen.
                            observerLinks.filter {
                                observerNoteAndLink.contains(
                                    NoteAndLink(uid, it.id)
                                )
                            }.forEach { _link ->
                                LinkCard(
                                    linkScreenModel = linkModel,
                                    noteAndLinkScreenModel = noteAndLinkModel,
                                    noteUid = uid,
                                    isSwipe = true,
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
                                        dataModel.addQuickData(
                                            QuickData(
                                                description = descriptionState.value,
                                                uid = uid,
                                                color = backgroundColorState.intValue,
                                                textColor = textColorState.intValue,
                                                priority = priorityState.value
                                            )
                                        ).invokeOnCompletion {
                                            action.invoke()
                                        }

                                    }, containerColor = MaterialTheme.colorScheme.outlineVariant,
                                    contentColor = contentColorFor(
                                        backgroundColor = MaterialTheme.colorScheme.outlineVariant
                                    )
                                ) {
                                    Icon(painterResource(CommonIcons.DONE_ICON), null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}