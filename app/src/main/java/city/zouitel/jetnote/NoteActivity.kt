package city.zouitel.jetnote

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ShapeDefaults
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import city.zouitel.applock.LockModeHandler
import city.zouitel.audio.player.PlaybackManager
import city.zouitel.rooted.RootCheckerHandler
import city.zouitel.screens.main_screen.MainScreen
import city.zouitel.screenshot.ScreenshotHandler
import city.zouitel.shortcuts.checkNoteActivityShortcut
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.MainTheme
import city.zouitel.widget.WidgetReceiver
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

class NoteActivity : AppCompatActivity(), KoinComponent, IntentHandler {

    private val dataStoreModel: DataStoreScreenModel by inject()
    private val playerManager: PlaybackManager by inject()
    private val activityScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Navigator(RootCheckerHandler())
            LockModeHandler(dataStoreModel)
            ScreenshotHandler(window, dataStoreModel)
            HideSystemUI(window)
            IntentHandler(intent) {}

            MainTheme(dataStoreModel) {
                BottomSheetNavigator(
                    sheetShape = ShapeDefaults.Large,
                    sheetElevation = 150.dp
                ) {
                    Navigator(MainScreen(true))
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        WidgetReceiver.updateBroadcast(this)
        // Cancel the performUiEvent when the activity is destroyed
        activityScope.cancel()
        playerManager.releaseController()
    }

    override fun onResume() {
        super.onResume()
        checkNoteActivityShortcut(this)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override val coroutineContext: CoroutineContext
        get() = GlobalScope.coroutineContext
}