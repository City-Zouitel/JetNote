package city.zouitel.api

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.domain.usecase.DataUseCase
import com.example.domain.usecase.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    application: Application,
    private val repo: FirestoreRepoImp,
): ViewModel() {

    private var workManager = WorkManager.getInstance(application)

    fun addDataToCloud(list: HashMap<String, ArrayList<String>>) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addData(list)
        }
    }

    fun doWork() = viewModelScope.launch(Dispatchers.IO) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val worker = OneTimeWorkRequest.Builder(Worker::class.java)
            .addTag("api_work")
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            "unique_api_worker",
            ExistingWorkPolicy.KEEP,
            worker
        )
    }
}
