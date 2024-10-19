package city.zouitel.systemDesign

import android.content.Context
import android.media.AudioManager

class SoundEffect {

    fun performSoundEffect(context: Context, effectType: Int, isMuted: Boolean) {
        val sysServ = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (isMuted.not()) sysServ.playSoundEffect(effectType, 1.0f)
    }
}
