package city.zouitel.jetnote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import city.zouitel.links.ui.LinkVM
import city.zouitel.navigation.Graph
import city.zouitel.shortcuts.checkNoteActivityShortcut
import city.zouitel.widget.WidgetReceiver
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class NoteActivity : ComponentActivity(), KoinComponent, IntentHandler {

    private val linkViewModel: LinkVM by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        setContent {

            val navHostController = rememberNavController()
            val scope = rememberCoroutineScope()

            intentHandler(intent, this@NoteActivity, navHostController, scope)

            MainTheme {
                Graph(navHostController)
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

    override fun onStart() {
        super.onStart()
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
}

