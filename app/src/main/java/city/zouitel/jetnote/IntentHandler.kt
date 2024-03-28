package city.zouitel.jetnote

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.navigation.NavHostController
import cafe.adriel.voyager.navigator.Navigator
import city.zouitel.logic.codeUrl
import city.zouitel.note.ui.add_screen.AddScreen
import city.zouitel.screens.home_screen.HomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

internal interface IntentHandler: CoroutineScope {

    fun intentHandler(
        intent: Intent,
        context: Context,
        navHC: NavHostController,
        navigator: Navigator?,
    ) {
        intent.apply {
            if (action == Intent.ACTION_SEND && type == "text/plain") {
                getStringExtra(Intent.EXTRA_TEXT)?.let {
                    launch {
                        navigator?.push(listOf(HomeScreen(), AddScreen(UUID.randomUUID().toString(), codeUrl(it))))
//                        navigator?.push(AddScreen(UUID.randomUUID().toString(), codeUrl(it)))
                    }
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
            if (action == Intent.ACTION_VIEW) {
                if (extras?.containsKey("new_note_shortcut") == true) {
                    getBooleanExtra("new_note_shortcut", false)
                    launch {
                        navigator?.push(AddScreen(UUID.randomUUID().toString()))
                    }
                }
                if (extras?.containsKey("quick_note") == true) {
                    getBooleanExtra("quick_note", false)
                    launch {
//                        navHC.navigate("${Cons.ADD_ROUTE}/${UUID.randomUUID()}/${Cons.NUL}")
                    }
                }
                if (extras?.containsKey("new_record") == true) {
                    getBooleanExtra("new_record", false)
                    launch {
//                        navHC.navigate("${Cons.ADD_ROUTE}/${UUID.randomUUID()}/${Cons.NUL}")
                    }
                }

            }
            removeCategory(Intent.CATEGORY_DEFAULT)
            action = null
        }
    }

}