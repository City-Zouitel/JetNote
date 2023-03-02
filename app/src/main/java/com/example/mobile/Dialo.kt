package com.example.mobile

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.CoroutineScope

@Composable
fun Dialo() {
    Screen(destination = "quick") {
        Dialog(
            onDismissRequest = { /*TODO*/ }
        ) {
            Column {
                TextField(value = "7987654654", onValueChange = {})
                Button(onClick = { /*TODO*/ }) {}
            }
        }
    }
}
@Composable
fun Screen(
    context: Context = LocalContext.current,
    destination: String,
    launchedEffect: suspend CoroutineScope.() -> Unit = {
        logScreenViewEvent(
            context = context,
            screenName = "quick"
        )
    },
    content: @Composable () -> Unit
) {
    content()
    LaunchedEffect(Unit, launchedEffect)
}

fun logScreenViewEvent(context: Context, screenName: String) {
    FirebaseAnalytics.getInstance(context).logEvent(
        FirebaseAnalytics.Event.SCREEN_VIEW, bundleOf(
            FirebaseAnalytics.Param.SCREEN_NAME to screenName,
            FirebaseAnalytics.Param.SCREEN_CLASS to screenName
        )
    )
}