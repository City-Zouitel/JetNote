package city.zouitel.recoder.ui

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.io.FileOutputStream

@Suppress("DEPRECATION")
class RecorderRepoImpl(private val context: Context): RecorderRepo {
    private var recorder: MediaRecorder? = null

    private fun initRecorder() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        MediaRecorder(context)
    } else {
        MediaRecorder()
    }

    override fun start(outPutFile: File) {
        initRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outPutFile).fd)
            prepare()

            recorder = this
            recorder?.start()
        }
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder = null
    }

    override fun resume() {
        recorder?.resume()

    }

    override fun pause() {
        recorder?.pause()
    }
}