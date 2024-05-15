package city.zouitel.logic

import android.content.Context
import android.media.AudioManager

context (Context)
infix fun Boolean.makeSoundFor(audioManager: Int) {
    val systemService = this@Context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    if (this) systemService.playSoundEffect(audioManager, 1.0f)
}