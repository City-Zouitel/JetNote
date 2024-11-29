package city.zouitel.assistant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.assistant.model.GeminiQuest

class AssistantScreen: Screen {
    @Composable
    override fun Content() {
        var textState by remember { mutableStateOf("") }
        val viewModel = getScreenModel<AssistantScreenModel>()
        val response by remember(viewModel, viewModel::getGenerativeResponse).collectAsState()

        Column {
            TextField(
                value = textState,
                onValueChange = { textState = it }
            )
            Button({ viewModel.sendPrompt(GeminiQuest(null, textState)) }) {
                Text("prompt")
            }
            HorizontalDivider(modifier = Modifier.padding(20.dp))
            Text(text = response)
        }
    }
}