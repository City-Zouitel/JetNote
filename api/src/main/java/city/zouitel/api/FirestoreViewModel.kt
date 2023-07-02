package city.zouitel.api

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes
import kotlin.time.minutes

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    application: Application,
    private val repo: FirestoreRepoImp,
): ViewModel() {

    private var workManager = WorkManager.getInstance(application)

    fun addDataToCloud(info: Info) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addData(info)
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
