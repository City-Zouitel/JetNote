package city.zouitel.links.ui

import android.app.Application
import android.content.Context
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.baha.url.preview.BahaUrlPreview
import com.baha.url.preview.IUrlPreviewCallback
import com.baha.url.preview.UrlInfoItem
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.links.mapper.LinkMapper
import city.zouitel.links.model.Link as InLink
import city.zouitel.links.worker.LinkWorker
import city.zouitel.systemDesign.Cons.JPEG
import city.zouitel.systemDesign.Cons.LINK_DIR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL

class LinkVM(
    application: Application,
    getAll: LinkUseCase.GetAllLinks,
    private val delete: LinkUseCase.DeleteLink,
    private val mapper: LinkMapper,
    private val linkPath: String
): ViewModel() {

    private val _getAllLinks = MutableStateFlow<List<InLink>>(emptyList())
    val getAllLinks: StateFlow<List<InLink>>
        get() = _getAllLinks
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

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
        ctx: Context,
        res: String?,
        title: MutableState<String>?,
        host: MutableState<String>?,
        img: MutableState<String>?
    ) = res?.let {
        BahaUrlPreview(it, object : IUrlPreviewCallback {
            override fun onComplete(urlInfo: UrlInfoItem) {
                viewModelScope.launch(Dispatchers.IO) {
                    urlInfo.apply {
                        title?.value = this.title
                        host?.value = URL(this.url).host
                        img?.value = this.image
                    }
                }
            }

            override fun onFailed(throwable: Throwable) {
                Toast.makeText(ctx, "Can't load link", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun imageDecoder(context: Context, id:String): ImageBitmap? {
        val path = "$linkPath$id.$JPEG"
        val bitImg = BitmapFactory.decodeFile(path)
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