package city.zouitel.security.screenshot

import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import city.zouitel.systemDesign.DataStoreScreenModel

@Composable
fun ScreenshotHandler(window: Window, dataStoreModel: DataStoreScreenModel) {
    val screenshotState by remember(dataStoreModel, dataStoreModel::getScreenshotBlock).collectAsState()
    LaunchedEffect(screenshotState) {
        if (screenshotState) {
            window.setFlags(
                LayoutParams.FLAG_SECURE,
                LayoutParams.FLAG_SECURE
            )
        } else
            window.clearFlags(LayoutParams.FLAG_SECURE)
    }
}