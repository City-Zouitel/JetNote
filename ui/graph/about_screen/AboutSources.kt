package com.example.grqph.about_screen

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
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.Icons.GITHUB_ICON
import com.example.common_ui.MatColors
import com.example.common_ui.MatColors.Companion.ON_SURFACE
import com.example.common_ui.MatColors.Companion.SURFACE
import com.example.common_ui.SoundEffect
import com.example.datastore.DataStore

@Composable
fun AboutSources() {

    val uri = LocalUriHandler.current
    val ctx = LocalContext.current
    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false)
    val sound = SoundEffect()

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
            sound.makeSound(ctx, KEY_CLICK,thereIsSoundEffect.value)

    }
}


@Composable
fun Opensource(
    txt: String,
    icon: @Composable () -> Unit,
    action: () -> Unit
) {
    val getMatColor = MatColors().getMaterialColor

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(.5f)
            .padding(50.dp),
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            containerColor = getMatColor(ON_SURFACE)
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
                color = getMatColor(SURFACE)
            )
        }
    }
}
