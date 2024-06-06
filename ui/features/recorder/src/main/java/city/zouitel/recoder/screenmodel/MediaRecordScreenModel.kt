package city.zouitel.recoder.screenmodel

import android.media.MediaRecorder
import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.systemDesign.CommonConstants.MP3
import java.io.File

class MediaRecordScreenModel(private val recPath: String): ScreenModel {

    private val RECORDER_SAMPLE_RATE = 44100

    fun buildMediaRecord(id: String): MediaRecorder {
        /**
         i've had the same Error -38, i found out that there is Another background service that uses
        the mic via (AudioRecord), when disabling the background service or reboot os, it worked.
         */
        return MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recPath + File.pathSeparator + id + "." + MP3)
            prepare()
        }
    }
}