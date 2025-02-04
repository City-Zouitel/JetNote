package city.zouitel.links.ui

import android.app.Application
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.domain.usecase.LinkUseCase
import city.zouitel.domain.utils.Action
import city.zouitel.domain.utils.withAsync
import city.zouitel.links.mapper.LinkMapper
import city.zouitel.links.model.Link
import city.zouitel.links.state.UiState
import city.zouitel.links.utils.Constants.ID
import city.zouitel.links.utils.Constants.LINK_DESCRIBE
import city.zouitel.links.utils.Constants.LINK_ICON
import city.zouitel.links.utils.Constants.LINK_IMG
import city.zouitel.links.utils.Constants.LINK_TAG
import city.zouitel.links.utils.Constants.LINK_TITLE
import city.zouitel.links.utils.Constants.UID
import city.zouitel.links.utils.Constants.UNIQUE_LINK_WORK
import city.zouitel.links.utils.Constants.URL
import city.zouitel.links.worker.LinkWorker
import city.zouitel.logic.withFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("DeferredResultUnused")
class LinkScreenModel(
    application: Application,
    private val _observeAll_: LinkUseCase.ObserveAll,
    private val _observeByUid_: LinkUseCase.ObserveByUid,
    private val deleteById: LinkUseCase.DeleteById,
    private val deleteByUid: LinkUseCase.DeleteByUid,
    private val mapper: LinkMapper
): ScreenModel {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    internal val uiState: StateFlow<UiState> = _uiState
        .withFlow(UiState())

    private val _observeAll: MutableStateFlow<List<Link>> = MutableStateFlow(emptyList())
    val observeAll: StateFlow<List<Link>> = _observeAll
        .withFlow(emptyList()) {
            withAsync {
                _observeAll_.invoke().collect { _observeAll.value = mapper.fromDomain(it) }
            }
        }

    private val _observeByUid: MutableStateFlow<List<Link>> = MutableStateFlow(emptyList())
    val observeByUid: StateFlow<List<Link>> = _observeByUid
        .withFlow(emptyList())

    private var workManager = WorkManager.getInstance(application)

    fun initializeUid(uid: String) = withAsync {
        _observeByUid_(uid).collect {
            _observeByUid.value = mapper.fromDomain(it)
        }
    }

    internal fun doWork(link: Link) = withAsync {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val worker = OneTimeWorkRequest.Builder(LinkWorker::class.java)
            .addTag(LINK_TAG)
            .setInputData(
                Data.Builder()
                    .putInt(ID, link.id)
                    .putString(UID, link.uid)
                    .putString(LINK_TITLE, link.title)
                    .putString(LINK_DESCRIBE, link.description)
                    .putString(URL, link.url)
                    .putString(LINK_IMG, link.image)
                    .putString(LINK_ICON, link.icon)
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

    fun sendAction(act: Action) {
        when(act) {
            is Action.DeleteById -> withAsync { deleteById(act.id as Int) }
            else -> throw NotImplementedError("Action not implemented: $act")
        }
    }


    /**
     * Extracts and returns a list of URLs found within a given string description.
     *
     * This function takes an optional string [des] as input, splits it into words,
     * filters out words that match a URL pattern (starting with "http://" or "https://"),
     * and returns the matching URLs in reversed order (because there will issue in updating without reverse it).
     *
     * @param des The optional string description to search for URLs.
     * @return A list of URLs found in the description, or null if the description is null or no URLs are found.
     * The URLs are returned in reversed order of their appearance in the original string.
     */
    val findUrlLink: (String?) -> List<String>? = { des ->
        des?.split(' ', '\n', '\t')?.filter { s -> s.matches("https?://.+".toRegex()) }?.reversed()
    }
}