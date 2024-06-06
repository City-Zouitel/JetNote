package city.zouitel.links.worker

import android.content.Context
import android.graphics.Bitmap
import androidx.work.*
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.domain.usecase.NoteAndLinkUseCase
import city.zouitel.links.mapper.LinkMapper
import city.zouitel.links.mapper.NoteAndLinkMapper
import city.zouitel.links.utils.Constants.DESCRIBE
import city.zouitel.links.utils.Constants.HOST
import city.zouitel.links.utils.Constants.IMG
import city.zouitel.links.utils.Constants.LINK_ID
import city.zouitel.links.utils.Constants.NOTE_ID
import city.zouitel.links.utils.Constants.TITLE
import city.zouitel.links.utils.Constants.URL
import city.zouitel.links.model.Link as InLink
import city.zouitel.links.model.NoteAndLink as InNoteAndLink
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream

class LinkWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val ioDeprecated: CoroutineDispatcher,
    private val addLink: LinkUseCase.AddLink,
    private val addNoteAndLink: NoteAndLinkUseCase.AddNoteAndLink,
    private val linkMapper: LinkMapper,
    private val noteAndLinkMapper: NoteAndLinkMapper,
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(ioDeprecated) {
        return@withContext try {
            val note_id_data = inputData.getString(NOTE_ID) ?: ""
            val link_id_data = inputData.getLong(LINK_ID, 0L)
            val title_data = inputData.getString(TITLE) ?: ""
            val description_data = inputData.getString(DESCRIBE) ?: ""
            val url_data = inputData.getString(URL) ?: ""
            val image_data = inputData.getString(IMG) ?: ""
            val host_data = inputData.getString(HOST) ?: ""

            addLink.invoke(
               linkMapper.toDomain(
                   InLink(
                       id = link_id_data,
                       url = url_data,
                       host = host_data,
                       image = image_data,
                       title = title_data,
                       description = description_data
                   )
               )
            )

            addNoteAndLink.invoke(
                noteAndLinkMapper.toDomain(
                    InNoteAndLink(
                        noteUid = note_id_data,
                        linkId = link_id_data
                    )
                )
            )

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

    }

    private fun saveImageLocally(img: Bitmap?, path:String, name:String?) {
        FileOutputStream(
            name?.let { File(path, it) }
        ).use {
            img?.compress(Bitmap.CompressFormat.JPEG, 100, it)
            it.flush()
        }
    }
}