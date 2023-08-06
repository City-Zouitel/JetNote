package com.example.mobile

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.content.Intent
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.graph.CONS.ADD_ROUTE
import com.example.graph.CONS.NUL
import com.example.graph.codeUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

//internal fun hideSystemUI(window: Window) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        WindowInsetsControllerCompat(window, View(window.context)).let { controller ->
//            controller.hide(WindowInsetsCompat.Type.systemBars())
//            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
//    }

//    private fun showSystemUI() {
//        WindowCompat.setDecorFitsSystemWindows(window, true)
//        WindowInsetsControllerCompat(window, mainContainer).show(WindowInsetsCompat.Type.systemBars())
//    }



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