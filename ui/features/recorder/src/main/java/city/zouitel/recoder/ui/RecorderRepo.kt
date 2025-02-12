package city.zouitel.recoder.ui

import java.io.File

interface RecorderRepo {
    fun start(outPutFile: File)
    fun stop()
    fun resume()
    fun pause()
}