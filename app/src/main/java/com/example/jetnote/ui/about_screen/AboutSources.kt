package com.example.jetnote.ui.about_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetnote.cons.KEY_CLICK
import com.example.jetnote.cons.ON_SURFACE
import com.example.jetnote.cons.SURFACE
import com.example.jetnote.fp.getMaterialColor
import com.example.jetnote.icons.GITHUB_ICON
import com.example.jetnote.ui.settings_screen.makeSound

@Composable
fun AboutSources() {

    val uri = LocalUriHandler.current
    val ctx = LocalContext.current
    val thereIsSoundEffect = com.example.datastore.DataStore(ctx).thereIsSoundEffect.collectAsState(false)

    Opensource(
        txt = "Project Source",
        icon = {
            Icon(
                painter = painterResource(GITHUB_ICON),
                contentDescription = ""
            )
        }
    ) {
        uri.openUri("https://github.com/City-Zouitel/JetNote")
            .makeSound(ctx, KEY_CLICK,thereIsSoundEffect.value)

    }
}


@Composable
fun Opensource(
    txt: String,
    icon: @Composable () -> Unit,
    action: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(.5f)
            .padding(50.dp),
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            containerColor = getMaterialColor(ON_SURFACE)
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp)
        ) {
            icon.invoke()
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = txt,
                fontSize = 20.sp,
                color = getMaterialColor(SURFACE)
            )
        }
    }
}
