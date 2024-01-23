package city.zouitel.systemDesign

import android.content.Context
import android.media.AudioManager

class SoundEffect {

    val makeSound: (ctx: Context, effectType: Int, thereIsSoundEffect: Boolean) -> Unit
        get() = { ctx, type, sound ->
            val sysServ = ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager

            if (sound) sysServ.playSoundEffect(type, 1.0f)
        }
}
