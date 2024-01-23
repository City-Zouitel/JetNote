package city.zouitel.navigation

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.navigation.NavHostController
import city.zouitel.systemDesign.Cons.ADD_ROUTE
import city.zouitel.systemDesign.Cons.NUL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

fun intentHandler(
    intent: Intent,
    ctx: Context,
    navHC: NavHostController,
    scope: CoroutineScope
) {
    intent.apply {
        if (action == Intent.ACTION_SEND && type == "text/plain") {
            getStringExtra(Intent.EXTRA_TEXT)?.let {
                scope.launch {
                    navHC.navigate("$ADD_ROUTE/${UUID.randomUUID()}/${codeUrl(it)}")
                }
                Toast.makeText(ctx, it, Toast.LENGTH_SHORT).show()
            }
        }
        if (action == Intent.ACTION_VIEW) {
            if (extras?.containsKey("new_shortcut") == true) {
                getBooleanExtra("new_shortcut", false)
                scope.launch {
                    navHC.navigate("$ADD_ROUTE/${UUID.randomUUID()}/$NUL")
                }
            }
            if (extras?.containsKey("quick_note") == true) {
                getBooleanExtra("quick_note", false)
                scope.launch {
                    navHC.navigate("$ADD_ROUTE/${UUID.randomUUID()}/$NUL")
                }
            }

        }
        removeCategory(Intent.CATEGORY_DEFAULT)
        action = null
    }
}
