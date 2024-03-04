package city.zouitel.jetnote

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import city.zouitel.links.ui.LinkVM
import city.zouitel.navigation.home_screen.HomeScreen
import city.zouitel.shortcuts.checkNoteActivityShortcut
import city.zouitel.widget.WidgetReceiver
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class NoteActivity : ComponentActivity(), KoinComponent, IntentHandler {

    private val linkViewModel: LinkVM by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            val navHostController = rememberNavController()
            val navigator = LocalNavigator.current

            intentHandler(intent, this@NoteActivity, navHostController, navigator)

            MainTheme {
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
        linkViewModel.urlPreview(this, null, null, null, null)?.cleanUp()
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

