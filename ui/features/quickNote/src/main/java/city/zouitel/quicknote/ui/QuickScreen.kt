package city.zouitel.quicknote.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.logic.events.UiEvent
import city.zouitel.quicknote.model.QuickData
import city.zouitel.systemDesign.CommonIcons
import java.util.UUID

data class QuickScreen(val action: () -> (Unit)): Screen {
    @Composable
    override fun Content() {
        val dataModel = getScreenModel<QuickDataScreenModel>()
        val linkModel = getScreenModel<LinkScreenModel>()

        Quick(dataModel, action = action)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun Quick(
        dataModel: QuickDataScreenModel,
        action: () -> Unit
    ) {
        val uid = UUID.randomUUID().toString()

        val descriptionState = remember { mutableStateOf("") }
        val backgroundColor = MaterialTheme.colorScheme.surface.toArgb()
        val backgroundColorState = rememberSaveable { mutableIntStateOf(backgroundColor) }
        val textColor = contentColorFor(MaterialTheme.colorScheme.surface).toArgb()
        val textColorState = rememberSaveable { mutableIntStateOf(textColor) }
        val priorityState = remember { mutableStateOf(4) }

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
                                    autoCorrectEnabled = false,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Default
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                    }
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    cursorColor = Color(textColorState.intValue)
                                )
                            )
                        }

                        // Link display.
                        item {
//                            findUrlLink(descriptionState.value) ?. let { links ->
//                                for (link in links) {
//                                    CacheLinks(
//                                        linkModel = linkModel,
//                                        uid = uid,
//                                        url = link
//                                    )
//                                }
//                            }
                            // for refresh this screen.
//                            observerLinks.forEach { _link ->
//                                LinkCard(
//                                    linkScreenModel = linkModel,
//                                    uid = uid,
//                                    isSwipe = true,
//                                    link = _link
//                                )
//                            }
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                SmallFloatingActionButton(
                                    onClick = {
                                        dataModel.sendUiEvent(UiEvent.Insert(
                                            QuickData(
                                                description = descriptionState.value,
                                                uid = uid,
                                                background = backgroundColorState.intValue,
                                                textColor = textColorState.intValue,
                                                priority = priorityState.value
                                            )
                                        ))
                                        action.invoke()

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