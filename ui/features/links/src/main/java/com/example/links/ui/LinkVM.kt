package com.example.links.ui

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.baha.url.preview.BahaUrlPreview
import com.baha.url.preview.IUrlPreviewCallback
import com.baha.url.preview.UrlInfoItem
import com.example.common_ui.Cons.JPEG
import com.example.domain.reposImpl.LinkRepoImpl
import com.example.links.worker.LinkWorker
import com.example.local.model.Link
import com.example.local.model.NoteAndLink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LinkVM @Inject constructor(
    application: Application,
    private val repo: LinkRepoImpl
): ViewModel() {

    private val _getAllLinks = MutableStateFlow<List<Link>>(emptyList())
    val getAllLinks: StateFlow<List<Link>>
        get() = _getAllLinks
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    //
    private var workManager = WorkManager.getInstance(application)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllLinks.collect {
                _getAllLinks.value = it
            }
        }
    }

//    fun addLink(link: Link) =
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.addLink(link)
//        }


    fun deleteLink(link: Link) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteLink(link)
        }
    }

    fun urlPreview(
        res: String?,
        title: MutableState<String>?,
        host: MutableState<String>?,
        img: MutableState<String>?
    ) = viewModelScope.launch(Dispatchers.IO) {
        res?.let {
            BahaUrlPreview(it, object : IUrlPreviewCallback {
                override fun onComplete(urlInfo: UrlInfoItem) {
                        urlInfo.apply {
                            title?.value = this.title
//                    description.value = this.description
                            host?.value = URL(this.url).host
//                            url?.value = this.url
                            img?.value = this.image
                        }
                }

                override fun onFailed(throwable: Throwable) {
//                    Toast.makeText(ctx, "Can't load link", Toast.LENGTH_SHORT).show()
                }
            }).fetchUrlPreview()
        }
    }

//    fun saveImageLocally(img:Bitmap?, path:String, name:String?) {
//        FileOutputStream(
//            name?.let { File(path, it) }
//        ).use {
//            img?.compress(Bitmap.CompressFormat.JPEG, 100, it)
//            it.flush()
//        }
//    }

    fun imageDecoder(context: Context, id:String): ImageBitmap? {
        val path = File(context.filesDir.path + "/" + "links_img", "$id.$JPEG")
        val bitImg = BitmapFactory.decodeFile(path.absolutePath)
        return bitImg?.asImageBitmap()
    }


    fun doWork(
        linkId: String,
        noteId: String,
        url: String,
        image: String,
        title: String,
        host: String
    ) = viewModelScope.launch(Dispatchers.IO) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val worker = OneTimeWorkRequest.Builder(LinkWorker::class.java)
            .addTag("link_work")
            .setInputData(
                Data.Builder()
                    .putString("note_id_data", noteId)
                    .putString("link_id_data", linkId)
                    .putString("title_data", title)
                    .putString("url_data", url)
                    .putString("image_data", image)
                    .putString("host_data", host)
                    .build()
            )
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            "unique_link_worker",
            ExistingWorkPolicy.KEEP,
            worker
        )
    }
}