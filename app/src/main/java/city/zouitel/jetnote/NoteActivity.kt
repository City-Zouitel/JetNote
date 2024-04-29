package city.zouitel.jetnote

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import city.zouitel.links.ui.LinkScreenModel
import city.zouitel.logic.asLongToast
import city.zouitel.screens.home_screen.HomeScreen
import city.zouitel.root.RootScreenModel
import city.zouitel.shortcuts.checkNoteActivityShortcut
import city.zouitel.systemDesign.DataStoreScreenModel
import city.zouitel.systemDesign.MainTheme
import city.zouitel.widget.WidgetReceiver
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

class NoteActivity : ComponentActivity(), KoinComponent, IntentHandler {

    private val linkScreenModel: LinkScreenModel by inject()
    private val rootScreenModel: RootScreenModel by inject()
    private val dataStoreModel: DataStoreScreenModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navigator = LocalNavigator.current
            val isDeviceRooted = rootScreenModel.isDeviceRooted.collectAsState()

            require(!isDeviceRooted.value.getOrNull()?.isDeviceRooted!!) {
                "Cannot run JetNote on rooted device!".asLongToast()
            }

            IntentHandler(
                intent,
                navigator,
                {}
            )

            MainTheme(dataStoreModel) {
                Navigator(HomeScreen())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        linkScreenModel.urlPreview(this, null, null, null, null)?.cleanUp()
        WidgetReceiver.updateBroadcast(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        checkNoteActivityShortcut(this)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override val coroutineContext: CoroutineContext
        get() = GlobalScope.coroutineContext
}

