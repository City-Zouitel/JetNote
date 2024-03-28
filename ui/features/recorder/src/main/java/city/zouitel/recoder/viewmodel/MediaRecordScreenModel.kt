package city.zouitel.recoder.viewmodel

import android.media.MediaRecorder
import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.systemDesign.Cons

class MediaRecordScreenModel(private val recPath: String) : ScreenModel {

    fun buildMediaRecord(uid: String?): MediaRecorder {
        /**
         i've had the same Error -38, i found out that there is Another background service that uses
        the mic via (AudioRecord), when disabling the background service or reboot os, it worked.
         */
        return MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recPath + "/" + uid + "." + Cons.MP3)
            prepare()
        }
    }
}