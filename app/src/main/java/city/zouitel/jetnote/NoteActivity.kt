package city.zouitel.jetnote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import city.zouitel.links.ui.LinkVM
import city.zouitel.navigation.home_screen.NavGraphs
import city.zouitel.shortcuts.checkNoteActivityShortcut
import city.zouitel.systemDesign.Cons.AUDIOS
import city.zouitel.systemDesign.Cons.IMAGES
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.widget.WidgetReceiver
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.spec.NavGraphSpec
import kotlinx.coroutines.*
import org.koin.androidx.compose.koinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.util.*

class NoteActivity : ComponentActivity(), KoinComponent, IntentHandler {

    private val linkViewModel: LinkVM by inject()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        setContent {

            val navHostController = rememberNavController()
            val scope = rememberCoroutineScope()

            intentHandler(intent, this@NoteActivity, navHostController, scope)

            AppTheme {
//                Graph(navHostController)
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }

    @Composable
    private fun AppTheme(
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        linkViewModel.urlPreview(this, null, null, null, null)?.cleanUp()
    }

    override fun onStart() {
        super.onStart()
        // TODO: move it to init module as work manager.
        File(this.filesDir.path + "/" + IMAGES).mkdirs()
        File(this.filesDir.path + "/" + AUDIOS).mkdirs()
        File(this.filesDir.path + "/" + "links_img").mkdirs()

//        mapOf(
//            "Coffee" to "Prepare hot coffee for my self.",
//            "Certification" to "Call instructor for complete details.",
//            "Team Meeting" to "Planning sprint log for next product application update.",
//            "Birthday Party" to "Tomorrow is my brother birthday there will be party at 7:00 pm.",
//            "Vacation Tickets" to  "Buy tickets for the family vacation.",
//            "Appointment" to "Health check up with physician."
//
//        ).forEach {
//            viewmodel.value.addNote(
//                Note(
//                    uid = UUID.randomUUID().toString(),
//                    title = it.key,
//                    description = it.value
//                )
//            )
//        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        checkNoteActivityShortcut(this)
    }

    override fun onPause() {
        super.onPause()
        WidgetReceiver.updateBroadcast(this)
    }


}

