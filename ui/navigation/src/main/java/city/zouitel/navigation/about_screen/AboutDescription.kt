package city.zouitel.navigation.about_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.systemDesign.Cons.APP_NAME
import city.zouitel.systemDesign.Cons.APP_VERSION
import city.zouitel.systemDesign.MaterialColors.Companion.ON_SURFACE
import city.zouitel.navigation.getMaterialColor

@Composable
fun AboutDescription() {
    Text(
        text = about,
        fontSize = 20.sp,
        color = getMaterialColor(ON_SURFACE),
        modifier = Modifier.padding(20.dp)
    )
}

val about = "$APP_NAME is a note-taking and todo management open-source application.\n\n" +
        " It is developed by City-Zouitel organization on github.\n\n" +
        " And It is intended for archiving and creating notes in which photos," +
        " audio and saved web links, numbers and map locations.\n\n" +
        "Version: $APP_VERSION"



