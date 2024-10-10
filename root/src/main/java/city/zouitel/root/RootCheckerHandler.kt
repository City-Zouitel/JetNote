package city.zouitel.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import city.zouitel.logic.asLongToast
import kotlin.system.exitProcess

class RootCheckerHandler: Screen {
    @Composable
    override fun Content() {
        RootCheckerHandler(getScreenModel())
    }
}

@Composable
private fun RootCheckerHandler(rootScreenModel: RootScreenModel) {

    val context = LocalContext.current
    val isDeviceRooted by remember(rootScreenModel, rootScreenModel::isDeviceRooted).collectAsState()

    LaunchedEffect(isDeviceRooted) {
        if (isDeviceRooted.getOrNull()?.isDeviceRooted == true) {
            context.run { "Cannot run JetNote on rooted device!".asLongToast() }
            exitProcess(0)
        }
    }
}
