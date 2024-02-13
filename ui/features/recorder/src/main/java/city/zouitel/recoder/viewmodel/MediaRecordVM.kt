package city.zouitel.recoder.viewmodel

import android.media.MediaRecorder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.systemDesign.Cons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MediaRecordVM : ViewModel() {

    fun buildMediaRecord(mediaPath:String): MediaRecorder {
        /**
         i've had the same Error -38, i found out that there is Another background service that uses
        the mic via (AudioRecord), when disabling the background service or reboot os, it worked.
         */
        return MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(mediaPath)
            prepare()
        }
    }
}