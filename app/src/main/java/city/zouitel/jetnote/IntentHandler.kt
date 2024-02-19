package city.zouitel.jetnote

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.navigation.NavHostController
import city.zouitel.logic.codeUrl
import city.zouitel.systemDesign.Cons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

internal interface IntentHandler {
    fun intentHandler(
        intent: Intent,
        context: Context,
        navHC: NavHostController,
        scope: CoroutineScope
    ) {
        intent.apply {
            if (action == Intent.ACTION_SEND && type == "text/plain") {
                getStringExtra(Intent.EXTRA_TEXT)?.let {
                    scope.launch {
                        navHC.navigate("${Cons.ADD_ROUTE}/${UUID.randomUUID()}/${codeUrl(it)}")
                    }
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
            if (action == Intent.ACTION_VIEW) {
                if (extras?.containsKey("new_note_shortcut") == true) {
                    getBooleanExtra("new_note_shortcut", false)
                    scope.launch {
                        navHC.navigate("${Cons.ADD_ROUTE}/${UUID.randomUUID()}/${Cons.NUL}")
                    }
                }
                if (extras?.containsKey("quick_note") == true) {
                    getBooleanExtra("quick_note", false)
                    scope.launch {
                        navHC.navigate("${Cons.ADD_ROUTE}/${UUID.randomUUID()}/${Cons.NUL}")
                    }
                }
                if (extras?.containsKey("new_record") == true) {
                    getBooleanExtra("new_record", false)
                    scope.launch {
                        navHC.navigate("${Cons.ADD_ROUTE}/${UUID.randomUUID()}/${Cons.NUL}")
                    }
                }

            }
            removeCategory(Intent.CATEGORY_DEFAULT)
            action = null
        }
    }

}