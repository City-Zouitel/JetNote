package city.zouitel.logic

import android.content.Context
import android.media.AudioManager
import android.widget.Toast

context (Context)
infix fun Boolean.makeSoundFor(audioManager: Int) {
    val systemService = this@Context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    if (this) systemService.playSoundEffect(audioManager, 1.0f)
}

context (Context)
fun String.asShortToast() {
    Toast.makeText(this@Context, this, Toast.LENGTH_SHORT).show()
}

context (Context)
fun String.asLongToast()  {
    Toast.makeText(this@Context, this, Toast.LENGTH_LONG).show()
}