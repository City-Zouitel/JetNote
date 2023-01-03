package com.example.jetnote.ui

import android.R
import android.net.MailTo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
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
            title = {
                Text(
                    text = APP_NAME,
                    fontSize = 30.sp,
                    color = getMaterialColor(ON_SURFACE),
                    style = TextStyle(textDirection = TextDirection.Ltr),
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                )
            },
            text = {
                Text(
                    text = about,
                    fontSize = 15.sp,
                    color = getMaterialColor(ON_SURFACE)
                )
            },
        )
}

val about = "$APP_NAME a note-taking and todo management open-source application.\n\n" +
        " It is developed by City-Zouitel organization on github.\n\n" +
        " And It is intended for archiving and creating notes in which photos," +
        " audio and saved web links, numbers and map locations.\n\n" +
        "Version: $APP_VERSION"