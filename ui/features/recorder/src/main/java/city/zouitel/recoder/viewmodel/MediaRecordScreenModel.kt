package city.zouitel.recoder.viewmodel

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi
import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.systemDesign.Cons

class MediaRecordScreenModel(
    private val context: Context,
    private val recPath: String
): ScreenModel {

    private val RECORDER_SAMPLE_RATE = 44100

    fun buildMediaRecord(uid: String): MediaRecorder {
        /**
         i've had the same Error -38, i found out that there is Another background service that uses
        the mic via (AudioRecord), when disabling the background service or reboot os, it worked.
         */
        return MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recPath)
            prepare()
        }
    }
}