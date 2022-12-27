package com.example.jetnote.ui

import android.net.MailTo
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.example.jetnote.cons.APP_NAME
import com.example.jetnote.cons.APP_VERSION
import com.example.jetnote.cons.ON_SURFACE
import com.example.jetnote.fp.getMaterialColor

@Composable
fun AppAbout(aboutDialogState: MutableState<Boolean>) {

    androidx.compose.material3.AlertDialog(
        onDismissRequest = { aboutDialogState.value = false },
        confirmButton = {},
        title = { Text(
            text = APP_NAME,
            fontSize = 26.sp,
            color = getMaterialColor(ON_SURFACE),
            style = TextStyle(textDirection = TextDirection.Ltr)
        ) },
        text = {
            Text(
                text = about,
                fontSize = 15.sp,
                color = getMaterialColor(ON_SURFACE)
            )
        }
    )
}

val about = "JetNote a note-taking and todo management application.\n\n" +
        " It is developed by City-Zouitel organization on github.\n\n" +
        " And It is intended for archiving and creating notes in which photos," +
        " audio and saved web links, numbers and map locations.\n\n" +
        "Version: $APP_VERSION"