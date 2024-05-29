package city.zouitel.systemDesign

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommonTextField(
    state: TextFieldState,
    modifier: Modifier,
    placeholder: String = "...",
    textSize: TextUnit = TextUnit(19f, TextUnitType.Sp),
    textColor: Color = Color.LightGray,
    imeAction : ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions()
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

        BasicTextField2(
            state = state,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.Transparent),
            textStyle = TextStyle(
                fontSize = textSize,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Default,
                color = textColor
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions
        )
    }
}