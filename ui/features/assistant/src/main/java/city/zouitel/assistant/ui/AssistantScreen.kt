package city.zouitel.assistant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.assistant.model.GeminiQuest
import city.zouitel.domain.utils.RequestState

class AssistantScreen: Screen {
    @Composable
    override fun Content() {
        var textState by remember { mutableStateOf("") }
        val viewModel = getScreenModel<AssistantScreenModel>()
        val response by remember(viewModel, viewModel::getGenerativeResponse).collectAsState(RequestState.Idle)

        Column {
            TextField(
                value = textState,
                onValueChange = { textState = it }
            )

            Button({ viewModel.sendPrompt(GeminiQuest(null, textState)) }) {
                Text("prompt")
            }

            response.DisplayResult(
                onLoading = { Text("Loading") },
                onSuccess = { Text(it) },
                onError = { Text(it) }
            )
        }
//        Column {
//            TextField(
//                value = textState,
//                onValueChange = { textState = it }
//            )
//            Button({ viewModel.sendPrompt(GeminiQuest(null, textState)) }) {
//                Text("prompt")
//            }
//            HorizontalDivider(modifier = Modifier.padding(20.dp))
//            Text(text = response)
//        }
    }
}