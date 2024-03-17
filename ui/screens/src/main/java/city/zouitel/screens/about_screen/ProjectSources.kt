package city.zouitel.screens.about_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.GITHUB_ICON
import city.zouitel.screens.sound
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProjectSources(
    dataStoreVM: DataStoreVM = koinViewModel()
) {

    val uri = LocalUriHandler.current
    val ctx = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

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
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(.5f)
            .padding(50.dp),
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp)
        ) {
            icon.invoke()
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = txt,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}


