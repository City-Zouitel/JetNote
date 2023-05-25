package com.example.links.worker

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.work.HiltWorker
import androidx.work.*
import coil.ImageLoader
import coil.request.ImageRequest
import com.baha.url.preview.BahaUrlPreview
import com.baha.url.preview.IUrlPreviewCallback
import com.baha.url.preview.UrlInfoItem
import com.example.common_ui.Cons
import com.example.domain.reposImpl.LinkRepoImpl
import com.example.domain.reposImpl.NoteAndLinkRepoImpl
import com.example.local.model.Link
import com.example.local.model.NoteAndLink
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okhttp3.internal.wait
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.*
import kotlin.time.Duration.Companion.seconds

@OptIn(DelicateCoroutinesApi::class)
@HiltWorker
class LinkWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val ioDeprecated: CoroutineDispatcher,
    private val linkRepo: LinkRepoImpl,
    private val noteAndLinkRepo: NoteAndLinkRepoImpl
): CoroutineWorker(context, workerParameters) {

    val linkImgPath = context.filesDir.path + "/" + "links_img"

    override suspend fun doWork(): Result = withContext(ioDeprecated) {
        return@withContext try {
            val note_id_data = inputData.getString("note_id_data") ?: ""
            val link_id_data = inputData.getString("link_id_data") ?: ""
            val title_data = inputData.getString("title_data") ?: ""
//            val description_data = inputData.getString("description_data") ?: ""
            val url_data = inputData.getString("url_data") ?: ""
            val image_data = inputData.getString("image_data") ?: ""
            val host_data = inputData.getString("host_data") ?: ""

            linkRepo.addLink(
                link = Link(
                    id = link_id_data,
                    url = url_data,
                    host = host_data,
                    image = image_data,
                    title = title_data,
                    description = null
                )
            )

            noteAndLinkRepo.addNoteAndLink(
                NoteAndLink(
                    noteUid = note_id_data,
                    linkId = link_id_data
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