package com.example.links.ui

import android.app.Application
import android.content.Context
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
import com.example.domain.model.Link as OutLink
import com.example.domain.usecase.LinkUseCase
import com.example.links.mapper.LinkMapper
import com.example.links.model.Link as InLink
import com.example.links.worker.LinkWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class LinkVM @Inject constructor(
    application: Application,
    getAll: LinkUseCase.GetAllLinks,
    private val delete: LinkUseCase.DeleteLink,
    private val mapper: LinkMapper
): ViewModel() {

    private val _getAllLinks = MutableStateFlow<List<InLink>>(emptyList())
    val getAllLinks: StateFlow<List<InLink>>
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
            getAll.invoke().collect { list ->
                _getAllLinks.value = list.map { mapper.toView(it) }
            }
        }
    }

    fun deleteLink(link: InLink) {
        viewModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(link))
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
                            host?.value = URL(this.url).host
                            img?.value = this.image
                        }
                }

                override fun onFailed(throwable: Throwable) {

                }
            }).fetchUrlPreview()
        }
    }

    fun imageDecoder(context: Context, id:String): ImageBitmap? {
        val path = File(context.filesDir.path + "/" + "links_folder", "$id.$JPEG")
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