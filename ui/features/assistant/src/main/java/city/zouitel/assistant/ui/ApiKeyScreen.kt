package city.zouitel.assistant.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.systemDesign.CommonBottomSheet
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonOptionItem
import city.zouitel.systemDesign.DataStoreScreenModel

data class ApiKeyScreen(val apiKey: String): Screen {
    val GEMINI_API_KEY_INFO = "https://ai.google.dev/gemini-api/docs/api-key"
    val GEMINI_MODELS_INFO = "https://ai.google.dev/gemini-api/docs/models/gemini"

    @Composable
    override fun Content() {
        val dsModel = getScreenModel<DataStoreScreenModel>()
        val navBottom = LocalBottomSheetNavigator.current
        val uri = LocalUriHandler.current
        var newApikey by remember { mutableStateOf("") }

        Navigator(CommonBottomSheet {

            Text(
                modifier = Modifier.padding(12.dp),
                text = "Api Configuration.",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = newApikey,
                onValueChange = { newApikey = it },
                placeholder = {
                    Text(
                        text = apiKey
                            .replaceRange(3, apiKey.length - 4, "************")
                            .ifEmpty { "Enter your api key" },
                        color = Color.Gray,
                        fontSize = 18.sp
                    ) },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable { uri.openUri(GEMINI_API_KEY_INFO) },
                        painter = painterResource(CommonIcons.EXCLAMATION_ICON),
                        contentDescription = "Info",
                        tint = Color.Gray
                    )
                },
                maxLines = 10,
                textStyle = TextStyle(fontSize = 20.sp),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.background,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(Modifier.height(50.dp))

            CommonOptionItem(
                onConfirm = {
                    dsModel.setGeminiApiKey(newApikey)
                    navBottom.hide()
                },
                onDismiss = { navBottom.hide() }
            )
        })
    }
}