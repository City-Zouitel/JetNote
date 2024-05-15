package city.zouitel.audios.audio

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import city.zouitel.audios.model.Audio
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException

class LocalMediaDataSource (
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher
) {

    private val contentResolver: ContentResolver get() = context.contentResolver
    private val audioProjection: Array<String> get() = arrayOf(
        MediaStore.Audio.AudioColumns._ID,
        MediaStore.Audio.AudioColumns.DATE_ADDED,
        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
        MediaStore.Audio.AudioColumns.DURATION,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.AudioColumns.SIZE
    )

    suspend fun loadAudioById(id: Long): Audio? = withContext(ioDispatcher) {
        val mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media._ID + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = contentResolver.query(mediaContentUri, audioProjection, selection, selectionArgs, null)
        return@withContext cursor?.use {
            if (it.moveToFirst()) it.toLocalAudio() else null
        }
    }

    suspend fun loadAudioFiles(query: String) : List<Audio> = withContext(ioDispatcher) {
        val contentUri = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ->
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            else -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }
        val content = mutableListOf<Audio?>()
        val cursor = contentResolver.query(
            contentUri,
            audioProjection,
            MediaStore.Audio.Media.DISPLAY_NAME + " LIKE '%$query%'",
            null,
            "${MediaStore.MediaColumns.DATE_ADDED} DESC"
        )
        cursor?.use {
            while (it.moveToNext()) {
                content.add(it.toLocalAudio())
            }
        }
        return@withContext content.filterNotNull()
    }

    private fun Cursor.toLocalAudio(): Audio? {
        try {
            val contentId = getColumnIndex(MediaStore.Audio.AudioColumns._ID).let(::getLong)
            val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentId)
            val data = getColumnIndex(MediaStore.Audio.Media.DATA).let(::getString)
            if(!File(data).exists()) {
                return null
            }
            return Audio(
                id = contentId,
                path = data,
                title = getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME).let(::getString),
                duration = getColumnIndex(MediaStore.Audio.AudioColumns.DURATION).let(::getLong),
                size = getColumnIndex(MediaStore.Audio.AudioColumns.SIZE).let(::getLong),
                uri = uri.toString(),
            )
        } catch (e: FileNotFoundException) {
            return null
        }
    }
}