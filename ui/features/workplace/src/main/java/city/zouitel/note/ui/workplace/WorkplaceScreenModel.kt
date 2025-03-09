package city.zouitel.note.ui.workplace

import android.app.Application
import android.content.ClipData
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.content.MediaType
import androidx.compose.foundation.content.TransferableContent
import androidx.compose.foundation.content.hasMediaType
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy.*
import androidx.work.NetworkType
import androidx.work.WorkManager
import androidx.work.workDataOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.logic.withFlow
import city.zouitel.note.state.WorkplaceUiState
import city.zouitel.note.worker.CopyMediaWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class WorkplaceScreenModel(application: Application): ScreenModel {
    
    private val _uiState: MutableStateFlow<WorkplaceUiState> = MutableStateFlow(WorkplaceUiState())
    internal var uiState: StateFlow<WorkplaceUiState> = _uiState
        .withFlow(WorkplaceUiState())

    private var workManager = WorkManager.getInstance(application)

    fun updatePriority(priority: Int): WorkplaceScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(priority = priority)
        }
        return this
    }

    fun updateBackgroundColor(color: Int): WorkplaceScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(backgroundColor = color)
        }
        return this
    }

    fun updateTextColor(color: Int): WorkplaceScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(textColor = color)
        }
        return this
    }

    fun updateTitleFieldFocused(value: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isTitleFieldFocused = value)
        }
    }

    fun updateDescriptionFieldFocused(value: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isDescriptionFieldFocused = value)
        }
    }

    internal fun handleContentReceiver(content: TransferableContent, uid: String) {
        if (content.hasMediaType(MediaType.All)) {
            val clipData = content.clipEntry.clipData
            for (index in 0 until clipData.itemCount) {
                val item: ClipData.Item = clipData.getItemAt(index)
                val uri = item.uri
                if (uri != null) {
                    enqueueFileProcessing(uid, uri)
                }
            }
        }
    }

    internal fun handleResultContracts(uid: String, uri: Uri) {
        enqueueFileProcessing(uid, uri)
    }

    private fun enqueueFileProcessing(uid: String, uri: Uri) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresStorageNotLow(true)
            .build()

//        val inputData = Data.Builder()
//            .putString("CONTENT_URI", uri.toString())
//            .putString("CONTENT_UID", uid)
//            .build()

        val inputData = workDataOf(
            "CONTENT_URI" to uri.toString(),
            "CONTENT_UID" to uid
        )

        val request = androidx.work.OneTimeWorkRequestBuilder<CopyMediaWorker>()
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(WORK_NAME, KEEP, request)
    }

    companion object {
        const val WORK_NAME = "unique_workplace_work"
    }
}