package city.zouitel.navigation

import android.content.Context
import android.media.AudioManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.Cons.KEY_STANDARD
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.SoundEffect

val sound = SoundEffect()

internal sealed class State {

    @Stable
    class Sound(
        private val viewModel: DataStoreVM
    ): State() {
        val soundState : @Composable () -> Unit get() = {

            if (remember(viewModel, viewModel::getSound).collectAsState().value) {
                (LocalContext.current.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
                    .playSoundEffect(AudioManager.FX_KEY_CLICK, 1f)
            }
        }
    }
}
