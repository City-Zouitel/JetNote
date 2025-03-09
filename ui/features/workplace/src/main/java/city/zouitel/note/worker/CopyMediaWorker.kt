package city.zouitel.note.worker

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.annotation.WorkerThread
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import city.zouitel.domain.usecase.MediaUseCase
import city.zouitel.media.mapper.MediaMapper
import city.zouitel.media.model.Media
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.random.Random

class CopyMediaWorker(
    context: Context,
    params: WorkerParameters,
    private val ioDeprecated: CoroutineDispatcher,
    private val insert: MediaUseCase.Insert,
    private val mapper: MediaMapper
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val mediaUri = inputData.getString("CONTENT_URI") ?: return Result.failure()
            val mediaUid = inputData.getString("CONTENT_UID") ?: return Result.failure()

            copyMediaToExternalStorage(applicationContext, mediaUid, Uri.parse(mediaUri))
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    @WorkerThread
    private suspend fun copyMediaToExternalStorage(context: Context, mediaUid: String, uri: Uri) {
        val contentResolver: ContentResolver = context.contentResolver

        val fileDescriptor = contentResolver.openFileDescriptor(uri, "r") ?: return

        val inputStream = try {
            FileInputStream(fileDescriptor.fileDescriptor)
        } catch (e: Exception) {
            fileDescriptor.close()
            return
        }
        val mediaType = context.contentResolver.getType(uri)
        val timeStamp = System.currentTimeMillis()
        val fileExtension = mediaType?.let {
            when {
                it.startsWith("image/png") -> ".png"
                it.startsWith("image/jpeg") -> ".jpg"
                it.startsWith("image/gif") -> ".gif"
                it.startsWith("video/mp4") -> ".mp4"
                it.startsWith("video/3gpp") -> ".3gp"
                else -> ""
            }
        }

        val fileName = "$timeStamp$fileExtension"

        // Determine the appropriate directory based on the media type.
        val externalDir = when {
            mediaType?.startsWith("image/") == true -> {
                Environment.DIRECTORY_PICTURES
            }
            mediaType?.startsWith("video/") == true -> {
                Environment.DIRECTORY_MOVIES
            }
            else -> {
                Environment.DIRECTORY_DOWNLOADS
            }
        } ?.let { context.getExternalFilesDir(it) }
        val file = File(externalDir, fileName)

        withContext(ioDeprecated) {
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            val res = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

            when {
                mediaType?.startsWith("video/") == true -> {
                    insert(mapper.toDomain(Media(Random.nextLong(), mediaUid, true, res.toString())))
                }
                mediaType?.startsWith("image/") == true -> {
                    insert(mapper.toDomain(Media(Random.nextLong(), mediaUid, false, res.toString())))
                }
                else -> throw Exception("")
            }
        }
        kotlin.runCatching {
            inputStream.close()
            fileDescriptor.close()
        }
    }
}