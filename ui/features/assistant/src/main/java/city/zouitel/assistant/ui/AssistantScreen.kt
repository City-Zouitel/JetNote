package city.zouitel.assistant.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.assistant.model.Message
import city.zouitel.systemDesign.CommonIcons.WIFI_SLASH_ICON
import dev.jeziellago.compose.markdowntext.MarkdownText

class AssistantScreen: Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<AssistantScreenModel>()

        val haptic = LocalHapticFeedback.current
        val keyboard = LocalSoftwareKeyboardController.current
        val messages by remember(viewModel, viewModel::observeAllMessages).collectAsState()
        var textState by remember { mutableStateOf("") }
        val connectivity by remember(viewModel, viewModel::networkStatus).collectAsStateWithLifecycle()
        val focus by lazy { FocusRequester() }
        val placeholder = remember { mutableStateOf("...") }

        LaunchedEffect(true) {
            focus.requestFocus()
        }

        when (connectivity) {
            true ->  {
                placeholder.value = "Type something.."
            }
            false -> {
                placeholder.value = "You are offline!"
            }
        }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (chatRef, messageRef) = createRefs()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .constrainAs(messageRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(chatRef.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
            ) {
                items(messages) { message ->
                    Box(
                        contentAlignment = if (message.isRequest) Alignment.CenterEnd else Alignment.CenterStart,
                        modifier = Modifier
                            .animateContentSize()
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        MarkdownText(
                            modifier = Modifier
                                .padding(12.dp)
                                .then(
                                    when {
                                        message.isRequest -> {
                                            Modifier.clip(
                                                RoundedCornerShape(
                                                    topStart = 48f,
                                                    topEnd = 0f,
                                                    bottomStart = 48f,
                                                    bottomEnd = 48f
                                                )
                                            )
                                                .background(Color.Gray)
                                        }

                                        !message.isRequest -> Modifier
                                        else -> {
                                            Modifier
                                                .clip(RoundedCornerShape(48f))
                                                .background(MaterialTheme.colorScheme.errorContainer)
                                        }
                                    }
                                        .padding(12.dp)
                                ),
                            isTextSelectable = true,
                            color = MaterialTheme.colorScheme.onSurface,
                            markdown = message.prompt
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .constrainAs(chatRef) {
                        top.linkTo(messageRef.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focus)
                        .background(Color.Transparent)
                        .padding(7.dp),
                    value = textState,
                    enabled = connectivity,
                    onValueChange = { textState = it },
                    trailingIcon = {
                        if (!connectivity)
                        Icon(painterResource(WIFI_SLASH_ICON), null)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = {
                        viewModel.insertMessage(Message(isRequest = true, prompt = textState))
                        textState = ""
                        keyboard?.hide()
                    }),
                    placeholder = {
                            Text(modifier = Modifier.animateContentSize(),text = placeholder.value)
                                  },
                    shape = RoundedCornerShape(26.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}