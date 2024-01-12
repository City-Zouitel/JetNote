package com.example.graph.top_action_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.Cons.KEY_INVALID
import com.example.common_ui.DataStoreVM
import com.example.common_ui.Icons.CIRCLE_ICON_18
import com.example.common_ui.Icons.CROSS_ICON
import com.example.common_ui.MaterialColors.Companion.SURFACE
import com.example.graph.getMaterialColor
import com.example.graph.sound
import com.example.tags.model.Tag

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun SearchField(
    dataStoreVM: DataStoreVM = hiltViewModel(),
    title: MutableState<String>,
    placeholder: String,
    tagEntity: MutableState<Tag>?
) {
    val ctx = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = title.value,
        onValueChange = { title.value = it },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 17.sp,
            color = contentColorFor(backgroundColor = getMaterialColor(SURFACE))
        ),
        leadingIcon = {
                    Icon(
                        painter = painterResource(id = CIRCLE_ICON_18),
                        contentDescription = null,
                        tint = tagEntity?.value?.color?.let { Color(it) } ?: Color.Transparent
                    )
        },
        trailingIcon = {
            if (title.value.isNotEmpty() || tagEntity?.value?.color != Color.Transparent.toArgb()) {
                Icon(
                    painter = painterResource(id = CROSS_ICON),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        sound.makeSound.invoke(ctx, KEY_INVALID, thereIsSoundEffect.value)
                        title.value = ""
                        tagEntity?.value = Tag()
                    }
                )
            }
        },
        placeholder = {
            Text(text = placeholder, fontSize = 17.sp)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color.DarkGray,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}
