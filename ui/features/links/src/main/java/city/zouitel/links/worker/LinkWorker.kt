package city.zouitel.links.worker

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.work.*
import coil.ImageLoader
import coil.request.ImageRequest
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.domain.usecase.NoteAndLinkUseCase
import city.zouitel.links.mapper.LinkMapper
import city.zouitel.links.mapper.NoteAndLinkMapper
import city.zouitel.systemDesign.Cons
import city.zouitel.systemDesign.Cons.LINK_DIR
import city.zouitel.links.model.Link as InLink
import city.zouitel.links.model.NoteAndLink as InNoteAndLink
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import java.io.File
import java.io.FileOutputStream

class LinkWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
    private val ioDeprecated: CoroutineDispatcher,
    private val addLink: LinkUseCase.AddLink,
    private val addNoteAndLink: NoteAndLinkUseCase.AddNoteAndLink,
    private val linkMapper: LinkMapper,
    private val noteAndLinkMapper: NoteAndLinkMapper,
): CoroutineWorker(context, workerParameters), KoinComponent {
    private val linkImgPath = context.filesDir.path + "/" + LINK_DIR

    override suspend fun doWork(): Result = withContext(ioDeprecated) {
        return@withContext try {
            val note_id_data = inputData.getString("note_id_data") ?: ""
            val link_id_data = inputData.getString("link_id_data") ?: ""
            val title_data = inputData.getString("title_data") ?: ""
//            val description_data = inputData.getString("description_data") ?: ""
            val url_data = inputData.getString("url_data") ?: ""
            val image_data = inputData.getString("image_data") ?: ""
            val host_data = inputData.getString("host_data") ?: ""

            addLink.invoke(
               linkMapper.toDomain(
                   InLink(
                       link_id_data, url_data, host_data,image_data, title_data, null
                   )
               )
            )

            addNoteAndLink.invoke(
                noteAndLinkMapper.toDomain(
                    InNoteAndLink(
                        note_id_data, link_id_data
                    )
                )
            )

            // save link image in local link images file.
            val il = ImageLoader(context)
            val ir = ImageRequest.Builder(context)
                .data(image_data)
                .target {
                    saveImageLocally(
                        img = it.toBitmap(),
                        path = linkImgPath,
                        name = "$link_id_data.${Cons.JPEG}"
                    )
                }
                .build()
            il.enqueue(ir)

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