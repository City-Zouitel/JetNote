package city.zouitel.note.utils

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType
import androidx.compose.foundation.content.contentReceiver
import androidx.compose.foundation.content.hasMediaType
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TextField(
    state: TextFieldState,
    receiver: (Uri) -> Unit,
    modifier: Modifier,
    placeholder: String = "...",
    textSize: TextUnit = TextUnit(19f, TextUnitType.Sp),
    textColor: Color = Color.LightGray,
    imeAction: ImeAction = ImeAction.Default,
    keyboardAction: KeyboardActionHandler = KeyboardActionHandler {  }
) {
    Box(
        modifier = Modifier.padding(top = 30.dp)
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = if (state.text.isEmpty()) placeholder else "",
            color = Color.Gray,
            fontSize = textSize,
        )

        BasicTextField(
            state = state,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.Transparent)
                .contentReceiver { content ->
                    if(content.hasMediaType(MediaType.Image)) {
                        val data = content.clipEntry.clipData
                        for (index in 0 until data.itemCount) {
                            val item = data.getItemAt(index)
                            receiver.invoke(item.uri)
                        }
                    }
                    content
                },
            textStyle = TextStyle(
                fontSize = textSize,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Default,
                color = textColor
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = imeAction
            ),
            onKeyboardAction = keyboardAction
        )
    }
}