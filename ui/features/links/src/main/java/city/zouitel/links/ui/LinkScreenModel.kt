package city.zouitel.links.ui

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.work.*
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.baha.url.preview.BahaUrlPreview
import com.baha.url.preview.IUrlPreviewCallback
import com.baha.url.preview.UrlInfoItem
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.links.mapper.LinkMapper
import city.zouitel.links.model.Link
import city.zouitel.links.state.UiState
import city.zouitel.links.utils.Constants.DESCRIBE
import city.zouitel.links.utils.Constants.HOST
import city.zouitel.links.utils.Constants.IMG
import city.zouitel.links.utils.Constants.LINK_ID
import city.zouitel.links.utils.Constants.LINK_TAG
import city.zouitel.links.utils.Constants.NOTE_ID
import city.zouitel.links.utils.Constants.TITLE
import city.zouitel.links.utils.Constants.UNIQUE_LINK_WORK
import city.zouitel.links.utils.Constants.URL
import city.zouitel.links.model.Link as InLink
import city.zouitel.links.worker.LinkWorker
import city.zouitel.logic.asShortToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.URL

class LinkScreenModel(
    application: Application,
    getAll: LinkUseCase.GetAllLinks,
    private val delete: LinkUseCase.DeleteLink,
    private val mapper: LinkMapper,
): ScreenModel {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState()
    )
    internal val uiState: StateFlow<UiState> = _uiState
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            UiState()
        )

    private val _getAllLinks: MutableStateFlow<List<Link>> = MutableStateFlow(
        emptyList()
    )
    val getAllLinks: StateFlow<List<InLink>> = _getAllLinks
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    private var workManager = WorkManager.getInstance(application)

    init {
        screenModelScope.launch(Dispatchers.IO) {
            getAll.invoke().collect { links ->
                _getAllLinks.value = mapper.fromDomain(links)
            }
        }
    }

    fun deleteLink(link: InLink) {
        screenModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(link))
        }
    }

    fun urlPreview(
        context: Context,
        res: String?,
    ) = res?.let {
        BahaUrlPreview(it, object : IUrlPreviewCallback {
            override fun onComplete(urlInfo: UrlInfoItem) {
                screenModelScope.launch(Dispatchers.IO) {
                    urlInfo.apply {
                        _uiState.value = _uiState.value.copy(
                            title = title,
                            description = description,
                            host = URL(url).host,
                            img = image
                        )
                    }
                }
            }

            override fun onFailed(throwable: Throwable) {
                context.apply { "Can't load link".asShortToast() }
            }
        })
    }

    fun doWork(
        noteId: String,
        link: Link
    ) = screenModelScope.launch(Dispatchers.IO) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val worker = OneTimeWorkRequest.Builder(LinkWorker::class.java)
            .addTag(LINK_TAG)
            .setInputData(
                Data.Builder()
                    .putString(NOTE_ID, noteId)
                    .putLong(LINK_ID, link.id)
                    .putString(TITLE, link.title)
                    .putString(DESCRIBE, link.description)
                    .putString(URL, link.url)
                    .putString(IMG, link.image)
                    .putString(HOST, link.host)
                    .build()
            )
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            UNIQUE_LINK_WORK,
            ExistingWorkPolicy.KEEP,
            worker
        )
    }
}