package com.example.mobile

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.mobile.CONS.ADD_ROUTE
import com.example.mobile.CONS.NUL
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
            if (this.extras?.containsKey("new_shortcut") == true) {
                getBooleanExtra("new_shortcut", false)
                scope.launch {
                    navHC.navigate("$ADD_ROUTE/${UUID.randomUUID()}/$NUL")
                }
            }
            if (this.extras?.containsKey("quick_note") == true) {
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
