package city.zouitel.navigation

import android.content.Context
import android.media.AudioManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.SoundEffect

val sound = SoundEffect()
