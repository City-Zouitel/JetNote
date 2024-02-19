package city.zouitel.jetnote

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import city.zouitel.systemDesign.DataStoreVM
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.compose.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@Composable
fun MainTheme(
    dataStoreVM: DataStoreVM = koinViewModel(),
    content: @Composable () -> Unit
) {
    val currentTheme = remember(dataStoreVM, dataStoreVM::getTheme).collectAsState()
    val systemUiController = rememberSystemUiController()
    val isDarkUi = isSystemInDarkTheme()

    val theme = when {
        isSystemInDarkTheme() -> darkColorScheme()
        currentTheme.value == "DARK" -> darkColorScheme()
        else -> lightColorScheme()
    }

    SideEffect {
        systemUiController.apply {
            setStatusBarColor(Color.Transparent, !isDarkUi)
            setNavigationBarColor(
                if (currentTheme.value == "DARK" || isDarkUi) Color(red = 28, green = 27, blue = 31)
                else Color(red = 255, green = 251, blue = 254)
            )
        }
    }

    MaterialTheme(colorScheme = theme, content = content)
}
